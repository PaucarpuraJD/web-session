package org.jotad.apiservlet.weapp.header.Interceptor;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.jotad.apiservlet.weapp.header.configs.MysqlConn;
import org.jotad.apiservlet.weapp.header.services.Servicexception;

import java.sql.Connection;
import java.util.logging.Logger;

@TransactionalJdbc
@Interceptor
public class TransactionalJdbcInterceptor {

    @Inject
    @MysqlConn
    private Connection conn;

    @Inject
    private Logger log;

    @AroundInvoke
    public Object transactional(InvocationContext invocation) throws Exception{
        if (conn.getAutoCommit()){
            conn.setAutoCommit(false);
        }
        try {
            log.info("-----> iniciando transaccion " + invocation.getMethod().getName() +
                    " de la clase " + invocation.getMethod().getDeclaringClass());
            Object resultado = invocation.proceed();
            conn.commit();
            log.info("-----> realizando commit y finalizacion transaccion " + invocation.getMethod().getName() +
                    " de la clase " + invocation.getMethod().getDeclaringClass());
            return resultado;
        }catch (Servicexception e){
            conn.rollback();
            throw e;
        }
    }
}

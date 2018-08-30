package com.nice01.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.Collection;

public class RedisSessionDao extends AbstractSessionDAO {
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        return sessionId;
    }

    protected Session doReadSession(Serializable sessionId) {

        return null;
    }

    public void update(Session session) throws UnknownSessionException {

    }

    public void delete(Session session) {

    }

    public Collection<Session> getActiveSessions() {
        return null;
    }
}

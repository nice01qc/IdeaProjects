package test;

import java.util.*;

/**
 * 用于集合类测试
 */
public class Me {
    /**
     * List
     */
    private List favorites = new ArrayList();

    public List getFavorites() {
        return favorites;
    }

    public void setFavorites(List favorites) {
        this.favorites = favorites;
    }

    /**
     * Set
     */
    private Set reads = new HashSet();

    public Set getReads() {
        return reads;
    }

    public void setReads(Set reads) {
        this.reads = reads;
    }

    /**
     * Map
     */
    private Map jop = new HashMap();

    public Map getJop() {
        return jop;
    }

    public void setJop(Map jop) {
        this.jop = jop;
    }

    /**
     * properties
     */
    private Properties mails = new Properties();

    public Properties getMails() {
        return mails;
    }

    public void setMails(Properties mails) {
        this.mails = mails;
    }


    @Override
    public String toString() {
        return "Me{" +
                "favorites=" + favorites +
                ", reads=" + reads +
                ", jop=" + jop +
                ", mails=" + mails +
                '}';
    }
}

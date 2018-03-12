package com.wanda.ffanad.crm.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wanda.ffanad.core.domains.Link;
import com.wanda.ffanad.core.services.LinkService;
import com.wanda.ffanad.crm.CRMApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CRMApplication.class)
@WebAppConfiguration
public class LinkServiceTest {

    @Autowired
    LinkService service;

    @Test
    public void addNewLink() {
        Link record = new Link();
        record.setName("测试123");
        record.setComment("下一步要用到的内容");
//        assertTrue(service.addNewLink(record) > 0);
    }

    @Test
    public void getLinks() {
        Link record = new Link();
        record.setName("测试123");
        Collection<Link> linkCollection = service.getLinks(record);
        assertEquals("下一步要用到的内容", linkCollection.iterator().next().getComment());
    }

//    @Test
//    public void getLinksByPage() {
//        Collection<Link> linkCollection = service.getLinksByPage(new Link(), 2, 20);
//        assertTrue(linkCollection.size() <= 20);
//    }
}

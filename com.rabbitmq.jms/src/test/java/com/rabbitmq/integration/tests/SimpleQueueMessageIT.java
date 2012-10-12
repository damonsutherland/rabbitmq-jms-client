package com.rabbitmq.integration.tests;

import javax.jms.BytesMessage;
import javax.jms.DeliveryMode;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import junit.framework.Assert;

import org.junit.Test;

import com.rabbitmq.jms.AbstractTestConnectionFactory;
import com.rabbitmq.jms.message.TestMessages;

/**
 * Integration test for simple point-to-point messaging.
 */
public class SimpleQueueMessageIT {

    static final String QUEUE_NAME = "test.queue."+SimpleQueueMessageIT.class.getCanonicalName();
    static final String MESSAGE = "Hello " + SimpleQueueMessageIT.class.getName();

    @Test
    public void testSendAndReceiveTextMessage() throws Exception {


        QueueConnection queueConn = null;
        try {
            QueueConnectionFactory connFactory = (QueueConnectionFactory) AbstractTestConnectionFactory.getTestConnectionFactory()
                                                                                               .getConnectionFactory();
            queueConn = connFactory.createQueueConnection();
            queueConn.start();
            QueueSession queueSession = queueConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);
            Queue queue = queueSession.createQueue(QUEUE_NAME);
            QueueSender queueSender = queueSession.createSender(queue);
            queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage message = queueSession.createTextMessage(MESSAGE);
            queueSender.send(message);
        } finally {
            queueConn.close();
        }
        try {
            QueueConnectionFactory connFactory = (QueueConnectionFactory) AbstractTestConnectionFactory.getTestConnectionFactory()
                                                                                               .getConnectionFactory();
            queueConn = connFactory.createQueueConnection();
            queueConn.start();
            QueueSession queueSession = queueConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);
            Queue queue = queueSession.createQueue(QUEUE_NAME);
            QueueReceiver queueReceiver = queueSession.createReceiver(queue);
            TextMessage message = (TextMessage) queueReceiver.receive();
            Assert.assertEquals(MESSAGE, message.getText());
        } finally {
            queueConn.close();
        }

    }

    @Test
    public void testSendAndReceiveBytesMessage() throws Exception {
        QueueConnection queueConn = null;
        try {
            QueueConnectionFactory connFactory = (QueueConnectionFactory) AbstractTestConnectionFactory.getTestConnectionFactory()
                                                                                               .getConnectionFactory();
            queueConn = connFactory.createQueueConnection();
            queueConn.start();
            QueueSession queueSession = queueConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);
            Queue queue = queueSession.createQueue(QUEUE_NAME);
            QueueSender queueSender = queueSession.createSender(queue);
            queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            BytesMessage message = queueSession.createBytesMessage();

            TestMessages.writeBytesMessage(message);
            queueSender.send(message);
        } finally {
            queueConn.close();
        }
        try {
            QueueConnectionFactory connFactory = (QueueConnectionFactory) AbstractTestConnectionFactory.getTestConnectionFactory()
                                                                                               .getConnectionFactory();
            queueConn = connFactory.createQueueConnection();
            queueConn.start();
            QueueSession queueSession = queueConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);
            Queue queue = queueSession.createQueue(QUEUE_NAME);
            QueueReceiver queueReceiver = queueSession.createReceiver(queue);
            BytesMessage message = (BytesMessage) queueReceiver.receive();
            TestMessages.readBytesMessage(message);
        } finally {
            queueConn.close();
        }

    }

    @Test
    public void testSendAndReceiveMapMessage() throws Exception {
        QueueConnection queueConn = null;
        try {
            QueueConnectionFactory connFactory = (QueueConnectionFactory) AbstractTestConnectionFactory.getTestConnectionFactory()
                                                                                               .getConnectionFactory();
            queueConn = connFactory.createQueueConnection();
            queueConn.start();
            QueueSession queueSession = queueConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);
            Queue queue = queueSession.createQueue(QUEUE_NAME);
            QueueSender queueSender = queueSession.createSender(queue);
            queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            MapMessage message = queueSession.createMapMessage();

            TestMessages.writeMapMessage(message);
            queueSender.send(message);
        } finally {
            queueConn.close();
        }
        try {
            QueueConnectionFactory connFactory = (QueueConnectionFactory) AbstractTestConnectionFactory.getTestConnectionFactory()
                                                                                               .getConnectionFactory();
            queueConn = connFactory.createQueueConnection();
            queueConn.start();
            QueueSession queueSession = queueConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);
            Queue queue = queueSession.createQueue(QUEUE_NAME);
            QueueReceiver queueReceiver = queueSession.createReceiver(queue);
            MapMessage message = (MapMessage) queueReceiver.receive();
            TestMessages.readMapMessage(message);
        } finally {
            queueConn.close();
        }

    }

    @Test
    public void testSendAndReceiveStreamMessage() throws Exception {
        QueueConnection queueConn = null;
        try {
            QueueConnectionFactory connFactory = (QueueConnectionFactory) AbstractTestConnectionFactory.getTestConnectionFactory()
                                                                                               .getConnectionFactory();
            queueConn = connFactory.createQueueConnection();
            queueConn.start();
            QueueSession queueSession = queueConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);
            Queue queue = queueSession.createQueue(QUEUE_NAME);
            QueueSender queueSender = queueSession.createSender(queue);
            queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            StreamMessage message = queueSession.createStreamMessage();
            TestMessages.writeStreamMessage(message);
            queueSender.send(message);
        } finally {
            queueConn.close();
        }
        try {
            QueueConnectionFactory connFactory = (QueueConnectionFactory) AbstractTestConnectionFactory.getTestConnectionFactory()
                                                                                               .getConnectionFactory();
            queueConn = connFactory.createQueueConnection();
            queueConn.start();
            QueueSession queueSession = queueConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);
            Queue queue = queueSession.createQueue(QUEUE_NAME);
            QueueReceiver queueReceiver = queueSession.createReceiver(queue);
            StreamMessage message = (StreamMessage) queueReceiver.receive();
            TestMessages.readStreamMessage(message);
        } finally {
            queueConn.close();
        }

    }

    @Test
    public void testSendAndReceiveObjectMessage() throws Exception {
        QueueConnection queueConn = null;
        try {
            QueueConnectionFactory connFactory = (QueueConnectionFactory) AbstractTestConnectionFactory.getTestConnectionFactory()
                                                                                               .getConnectionFactory();
            queueConn = connFactory.createQueueConnection();
            queueConn.start();
            QueueSession queueSession = queueConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);
            Queue queue = queueSession.createQueue(QUEUE_NAME);
            QueueSender queueSender = queueSession.createSender(queue);
            queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            ObjectMessage message = queueSession.createObjectMessage();
            TestMessages.writeObjectMessage(message);
            queueSender.send(message);
        } finally {
            queueConn.close();
        }
        try {
            QueueConnectionFactory connFactory = (QueueConnectionFactory) AbstractTestConnectionFactory.getTestConnectionFactory()
                                                                                               .getConnectionFactory();
            queueConn = connFactory.createQueueConnection();
            queueConn.start();
            QueueSession queueSession = queueConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);
            Queue queue = queueSession.createQueue(QUEUE_NAME);
            QueueReceiver queueReceiver = queueSession.createReceiver(queue);
            ObjectMessage message = (ObjectMessage) queueReceiver.receive();
            TestMessages.readObjectMessage(message);
        } finally {
            queueConn.close();
        }

    }

}
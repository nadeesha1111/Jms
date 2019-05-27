
import com.eviware.soapui.impl.wsdl.submit.transports.jms.JMSConnectionHolder
import com.eviware.soapui.impl.wsdl.submit.transports.jms.util.HermesUtils
import com.eviware.soapui.impl.wsdl.submit.transports.jms.JMSEndpoint
import hermes.Hermes
import javax.jms.*

def jmsEndpoint = new  JMSEndpoint("jms://ActiveMQ3::topic_topic.t::topic_topic.t");
def hermes = HermesUtils.getHermes( context.testCase.testSuite.project, jmsEndpoint.sessionName)
def jmsConnectionHolder = new JMSConnectionHolder( jmsEndpoint, hermes, false, null ,null ,null);
looping = true


Session topicSession = jmsConnectionHolder.getSession();
Topic topicSend = jmsConnectionHolder.getTopic( jmsConnectionHolder.getJmsEndpoint().getSend() );
Topic topicBrowse = jmsConnectionHolder.getTopic( jmsConnectionHolder.getJmsEndpoint().getReceive() );

 TopicSubscriber consumer = topicSession.createSubscriber(topicBrowse);

MessageProducer messageProducer =topicSession.createPublisher(topicSend );
TextMessage textMessageSend = topicSession.createTextMessage(); 

while(looping) {
       Message message = consumer.receive(10000);
        println "message received!"
log.info "message received!";
       log.info message;
        if (message instanceof Message) { 
            println message.getText();
             log.info message.getText();
              log.info message;

textMessageSend.setText( "jms message from groovy0(acknowledgment )");
messageProducer.send( textMessageSend );


 //time dealy 
long time = 10 * 1000;
Thread.sleep(time)


textMessageSend.setText( "jms message from groovy0(response )");
messageProducer.send( textMessageSend );
              
            looping = false
        }
    }
 



jmsConnectionHolder.closeAll()

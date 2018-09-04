package pact;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Rule;
import org.junit.Test;
import utils.Configuration;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GetHelloWorldTest
{
    @Rule
    public PactProviderRuleMk2 rule = new PactProviderRuleMk2("ExampleProvider", this);
    private DslPart helloWorldResults;

    @Pact(consumer = Configuration.DUMMY_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        helloWorldResults = new PactDslJsonBody()
                .id("id",21341245L)
                .stringType("content","test")
                .close();

        return builder
                .given("")
                .uponReceiving("get hello world response")
                    .path("/hello-world")
                    .method("GET")
                .willRespondWith()
                    .status(200)
                    .headers(Configuration.getHeaders())
                    .body(helloWorldResults.asBody())
                .toPact();
    }

    @Test
    @PactVerification()
    public void runTest() throws IOException {
        DummyConsumer restClient = new DummyConsumer(rule.getUrl());
        assertEquals(helloWorldResults.toString(), restClient.getHelloWorld());
    }
}

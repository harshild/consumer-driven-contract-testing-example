package pact;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.amazonaws.http.HttpMethodName;
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
    DslPart postNameResults;

    @Pact(consumer = Configuration.DUMMY_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        helloWorldResults = new PactDslJsonBody()
                .id("id",21341245L)
                .stringType("content","test")
                .close();



        postNameResults = new PactDslJsonBody()
                .id("id", 21341245L)
                .stringMatcher("content", "[a-zA-Z]+\\s(abc!)","Hello abc!")
                .close();


        return builder
                .given("Hello World  Test")
                .uponReceiving("get hello world response")
                    .path("/hello-world")
                    .method(HttpMethodName.GET.name())
                .willRespondWith()
                    .status(200)
                    .headers(Configuration.getHeaders())
                    .body(helloWorldResults.asBody())
                .given("Hello with specific name")
                .uponReceiving("Name to post for hello world response")
                    .method(HttpMethodName.GET.name())
                    .path("/hello-world")
                    .matchQuery("name","abc")
                .willRespondWith()
                    .status(200)
                    .headers(Configuration.getHeaders())
                    .body(postNameResults)
                .given("POST Api test")
                    .uponReceiving("Name to post for hello world response")
                    .method(HttpMethodName.POST.name())
                    .path("/hello-world-post")
                    .body("abc")
                .willRespondWith()
                    .status(201)
                    .headers(Configuration.getHeaders())
                    .body(postNameResults)
                .toPact();
    }

    @Test
    @PactVerification()
    public void runTest() throws IOException {
        DummyConsumer restClient = new DummyConsumer(rule.getUrl());
        assertEquals(helloWorldResults.toString(), restClient.getHelloWorld());
        assertEquals(postNameResults.toString(), restClient.getHello("abc"));
        assertEquals(postNameResults.toString(), restClient.postHello("abc"));

    }
}

package pact;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class DummyConsumer {
    private String url;

    public DummyConsumer(String url) {
        this.url = url;
    }

    public String getHelloWorld() throws IOException{
        HttpResponse response = getResponse("/hello-world?name=sadsad");
        return getEntityAsString(response);
    }

    private String getEntityAsString(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
    }

    private HttpResponse getResponse(String path) throws IOException {
        return Request.Get(url + path)
                .execute().returnResponse();
    }

    public String postName() throws IOException {
        HttpResponse response = Request.Post(url + "/readName")
                .execute().returnResponse();
        return getEntityAsString(response);
    }
}

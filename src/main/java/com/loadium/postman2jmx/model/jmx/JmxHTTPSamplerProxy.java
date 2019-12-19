package com.loadium.postman2jmx.model.jmx;

import com.loadium.postman2jmx.model.postman.PostmanItem;
import com.loadium.postman2jmx.model.postman.PostmanQuery;
import com.loadium.postman2jmx.utils.UrlUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.testelement.TestElement;

import java.net.MalformedURLException;
import java.net.URL;

public class JmxHTTPSamplerProxy {
    public static HTTPSamplerProxy newInstance(PostmanItem postmanItem) {
        HTTPSamplerProxy httpSamplerProxy = new HTTPSamplerProxy();
        ArrayList<String> errorStatusCode = new ArrayList<String>();
        boolean error = false;
        errorStatusCode.add("422");
        errorStatusCode.add("406");
        errorStatusCode.add("412");
        errorStatusCode.add("400");
        errorStatusCode.add("403");
        errorStatusCode.add("404");
        errorStatusCode.add("401");
        errorStatusCode.add("402");
        errorStatusCode.add("405");
        errorStatusCode.add("407");
        errorStatusCode.add("408");
        errorStatusCode.add("409");
        errorStatusCode.add("410");
        errorStatusCode.add("411");
        errorStatusCode.add("413");
        errorStatusCode.add("414");
        errorStatusCode.add("415");
        errorStatusCode.add("416");
        errorStatusCode.add("417");
        errorStatusCode.add("418");
        errorStatusCode.add("420");
        errorStatusCode.add("426");
        errorStatusCode.add("429");
        errorStatusCode.add("449");
        errorStatusCode.add("451");
        errorStatusCode.add("failure");
        httpSamplerProxy.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSamplerProxy.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
        httpSamplerProxy.setName(postmanItem.getName());
        String name = postmanItem.getName();
        for (String item : errorStatusCode) {
			if(name.contains(item)) {
				error = true;
			}
		}
        if(error) {
        	httpSamplerProxy.setEnabled(false);
        }else {
        	httpSamplerProxy.setEnabled(true);
        }
        error=false;
        httpSamplerProxy.setAutoRedirects(false);
        httpSamplerProxy.setFollowRedirects(true);
        httpSamplerProxy.setUseKeepAlive(true);
        httpSamplerProxy.setMonitor(false);
        httpSamplerProxy.setDoMultipartPost(false);
        httpSamplerProxy.setComment("");
        httpSamplerProxy.setConnectTimeout("");
        httpSamplerProxy.setResponseTimeout("");
        httpSamplerProxy.setEmbeddedUrlRE("");
        httpSamplerProxy.setContentEncoding("");
        httpSamplerProxy.setMethod(postmanItem.getRequest().getMethod());


        String rawUrl = postmanItem.getRequest().getUrl().getRaw();

        if (UrlUtils.isVariableUrl(rawUrl)) {
            String url = UrlUtils.getVariableUrl(rawUrl);
            String path = UrlUtils.getVariablePath(rawUrl);

            httpSamplerProxy.setDomain(url);
            httpSamplerProxy.setPath(path);
        } else {
            URL url = null;

            try {
                url = UrlUtils.getUrl(rawUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String domain = UrlUtils.getDomain(url);
            int port = UrlUtils.getPort(url);
            String path = UrlUtils.getPath(url);
            String protocol = UrlUtils.getProtocol(url);

            httpSamplerProxy.setDomain(domain);
            httpSamplerProxy.setPath(path);

            if (port != -1) {
                httpSamplerProxy.setPort(port);
            }
            httpSamplerProxy.setProtocol(protocol);
        }


        Arguments arguments = new Arguments();
        HTTPArgument argument;


        for (PostmanQuery query : postmanItem.getRequest().getUrl().getQueries()) {
            String queryValue=query.getValue();
            argument = new HTTPArgument();
            argument.setName(query.getKey());
            if(query.getValue().contains("{{") && query.getValue().contains("}}")){
            	queryValue=query.getValue().replace("{{","${").replace("}}","}");
            }
            argument.setValue(queryValue);
            argument.setDescription(query.getKey());
            argument.setEnabled(true);
            argument.setMetaData("=");
            argument.setAlwaysEncoded(false);
            argument.setUseEquals(true);
            arguments.addArgument(argument);
        }
        httpSamplerProxy.setArguments(arguments);

        return httpSamplerProxy;
    }
}

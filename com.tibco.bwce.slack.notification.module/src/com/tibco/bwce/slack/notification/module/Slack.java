package com.tibco.bwce.slack.notification.module;

import java.io.IOException;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

public class Slack {

	public static void sendNotification(String appName)  {

		try {
			SlackSession session = SlackSessionFactory
					.createWebSocketSlackSession("xoxb-39598140755-vlwunlG4J5YJazfxLn19L00n");
			session.connect();
			try {
				SlackChannel channel = session.findChannelByName("tn2016-demo");

				if (System.getenv("VCAP_APPLICATION") != null) {
					// This is PCF environment
					session.sendMessage(channel,
							"BWCE Application ["+appName+"] is deployed to CloudFoundry.");
				} else if (System.getenv("KUBERNETES_SERVICE_HOST") != null) {
					// This is K8S environment
					session.sendMessage(channel,
							"BWCE Application ["+appName+"]  is deployed to Kubernetes.");
				} else {
					session.sendMessage(channel,
							"BWCE Application ["+appName+"]  is deployed to Local Environment.");
				}
			} finally {
				session.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

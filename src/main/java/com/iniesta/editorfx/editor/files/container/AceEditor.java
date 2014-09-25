package com.iniesta.editorfx.editor.files.container;

import java.net.URL;

import com.iniesta.editorfx.editor.UpdateNotification;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class AceEditor implements Container {

	private WebView webView;
	private SimpleBooleanProperty changed;
	private SimpleStringProperty text;
	private UpdateNotification notification;
	
	
	public AceEditor(UpdateNotification updateNotification){
		this.notification = updateNotification;
		webView = new WebView();
		initialize(webView);
		changed = new SimpleBooleanProperty(false);
		text = new SimpleStringProperty();
		
		webView.setOnKeyPressed(new EventHandler<Event>() {
			public void handle(Event arg0) {
				changed.set(true);
			}
		});
	}
	
	private void initialize(WebView webView) {
		WebEngine engine = webView.getEngine();
		Worker<Void> loadWorker = engine.getLoadWorker();
		notification.getProgressIndicator().progressProperty().bind(loadWorker.progressProperty());
		notification.getProgressIndicator().visibleProperty().bind(loadWorker.runningProperty());
		URL resource = getClass().getClassLoader().getResource("com/iniesta/editorfx/editor/ace/editor.html");
		System.out.println("Loading resource: " + resource.getPath());
		engine.loadContent(getContent());
		
	}

	private String getContent() {
		return "<!DOCTYPE html>"
				+ "<html lang=\"en\">"
				+ "<head>"
				+ "<title>ACE in Action</title>"
				+ "<style type=\"text/css\" media=\"screen\">"
				+ "    #editor {"
				+ "        position: absolute;"
				+ "        top: 0;"
				+ "        right: 0;"
				+ "        bottom: 0;"
				+ "        left: 0;"
				+ "    }"
				+ "</style>"
				+ "</head>"
				+ "<body>"
				+ "<div id=\"editor\">function foo(items) {"
				+ "    var x = \"All this is syntax highlighted\";"
				+ "    return x;"
				+ "}</div>"
				+ "    "
				+ "<script src=\""+getAceJs()+"\" type=\"text/javascript\" charset=\"utf-8\"></script>"
				+ "<script>"
				+ "    var editor = ace.edit(\"editor\");"
				+ "    editor.setTheme(\"ace/theme/monokai\");"
				+ "    editor.getSession().setMode(\"ace/mode/javascript\");"
				+ "</script>"
				+ "</body>"
				+ "</html>";
	}

	private String getAceJs() {
		String js = "com/iniesta/editorfx/editor/ace/js/src-noconflict/ace.js";
		js = "/home/antonio/dev/github/editorfx/target/classes/com/iniesta/editorfx/editor/ace/js/src-noconflict/ace.js";
		return js;
	}

	public Node getNode() {
		return webView;
	}

	public StringProperty textProperty() {
		return text;
	}

	public BooleanProperty changedProperty() {
		return changed;
	}

	public String getText() {
		return text.get();
	}

	public boolean isChanged() {
		return changed.get();
	}

}

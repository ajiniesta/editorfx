package com.iniesta.editorfx.editor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import com.iniesta.editorfx.editor.files.container.Container;

public class AceEditor implements Container {

	private final static String JAR_FILE_PROTOCOL = "jar:file:///";
	private final static String FILE_PROTOCOL = "file://";

	private WebView webView;
	private SimpleBooleanProperty changed;
	private SimpleStringProperty text;
	private UpdateNotification notification;

	public AceEditor(UpdateNotification updateNotification) {
		this.notification = updateNotification;
		webView = new WebView();
		changed = new SimpleBooleanProperty(false);
		text = new SimpleStringProperty("");

		webView.setOnKeyPressed(new EventHandler<Event>() {
			public void handle(Event arg0) {
				changed.set(true);
			}
		});

		text.addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(arg2!=null){
//					loadContent(webView);
				}
			}
		});
		initialize(webView);
	}

	private void initialize(WebView webView) {
		WebEngine engine = webView.getEngine();
		Worker<Void> loadWorker = engine.getLoadWorker();
		notification.getProgressIndicator().progressProperty().bind(loadWorker.progressProperty());
		notification.bind(loadWorker.runningProperty());
//		URL resource = getClass().getClassLoader().getResource("com/iniesta/editorfx/editor/ace/editor.html");
//		System.out.println("Loading resource: " + resource.getPath());
		loadContent(webView);
//		engine.load("http://www.google.es");
	}

	private void loadContent(WebView webView) {
		WebEngine engine = webView.getEngine();
		engine.loadContent(getContent());
	}

	private String getContent() {
		return "<!DOCTYPE html>" //
				+ "<html lang=\"en\">" //
				+ "<head>" //
				+ "<title>Editor</title>" //
				+ "<style type=\"text/css\" media=\"screen\">" //
				+ "    #editor {" //
				+ "        position: absolute;" //
				+ "        top: 10;" //
				+ "        right: 0;" //
				+ "        bottom: 10;" //
				+ "        left: 0;" //
				+ "    }" //
				+ "</style>" //
				+ "</head>" //
				+ "<body>" //
				+ "<div id=\"editor\"> " + text.get() + "</div>" //
				+ "    " //
				+ "<script src=\"" + getAceJs() + "\" type=\"text/javascript\" charset=\"utf-8\"></script>" //
				+ "<script>" //
				+ "    var editor = ace.edit(\"editor\");" //
				+ "    editor.setTheme(\"ace/theme/monokai\");" //
				+ "    editor.getSession().setMode(\"ace/mode/javascript\");" //
				+ "</script>" //
				+ "</body>" //
				+ "</html>"; //
	}

	private String getAceJs() {
		String js = getHTMLResourcePath("ace/js/src-noconflict/ace.js");
		return js;
	}
	
	/**
	 * Translate the html resource path
	 * @param resource
	 * @return
	 */
	private String getHTMLResourcePath(String resource) {
        String resourceBase = getClass().getPackage().getName();
        resourceBase = "/" + resourceBase.replace(".", "/") + "/";
        String path = getClass().getResource(resourceBase + resource).getPath();
        //resource in jar file
        if (path.startsWith("file:/")) {
            return path.replace("file:/", JAR_FILE_PROTOCOL);
        } else {
            return FILE_PROTOCOL + path;
        }
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

/** 
 * Copyright [2013] Antonio J. Iniesta
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * 
 * File created: 02/02/2013 at 02:28:19 by antonio
 */
package com.iniesta.editorfx;

import java.io.IOException;

import com.iniesta.editorfx.editor.Editor;
import com.iniesta.layerfx.FXMLHandler;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author antonio
 *
 */
public class EditorFX extends Application {

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage arg0) throws Exception {
		start();
	}

	protected static void start() throws IOException {
		long t1 = (new java.util.Date()).getTime();
		FXMLHandler handler = new FXMLHandler(Editor.class);
		handler.getHandlingView();
		handler.setStageStyle(StageStyle.DECORATED);
		handler.showWindow("Editor FX");
		long time = (new java.util.Date()).getTime() - t1;
		System.out.println("Time in loading..."+time+" ms");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}

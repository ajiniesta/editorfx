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
 * File created: 03/02/2013 at 12:15:20 by antonio
 */
package com.iniesta.editorfx.editor.files;

import java.io.File;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

/**
 * @author antonio
 *
 */
public class HelperFileCreator {

	public static void createNewFile(TabPane paneFiles, ProgressIndicator progress, File selectedFile) {		
		if(paneFiles!=null && selectedFile!=null){
			Tab tab = new Tab(selectedFile.getName());
			final TextArea tarea = new TextArea();
			tab.setContent(tarea);
			paneFiles.getTabs().add(tab);
			
			//Call the service to fill the text
			ServiceFileText service = new ServiceFileText(selectedFile);
			progress.visibleProperty().bind(service.runningProperty());
			tarea.textProperty().bind(service.valueProperty());
			if(selectedFile.canWrite()){
				SimpleBooleanProperty finish = new SimpleBooleanProperty(true);
				finish.bind(service.runningProperty());
				finish.addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> arg0,
							Boolean oldValue, Boolean newValue) {
						if(!newValue){
							System.out.println("Load Finished!");
							tarea.textProperty().unbind();
						}
					}
				});
			}
			service.start();
		}
	}

}

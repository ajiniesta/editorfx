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
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

/**
 * @author antonio
 *
 */
public class HelperFileCreator {

	private static int counter = 1;

	private static HelperFileCreator instance;

	private TabPane paneFiles;

	private ProgressIndicator progressIndicator;
	
	private Map<Tab, File> files;
	
	private HelperFileCreator(TabPane paneFiles, ProgressIndicator progressIndicator){
		super();
		this.paneFiles = paneFiles;
		this.progressIndicator = progressIndicator;
		files = new HashMap<Tab, File>();
	}
	
	public static HelperFileCreator getInstance(TabPane paneFiles, ProgressIndicator progressIndicator){
		if(instance==null){
			instance = new HelperFileCreator(paneFiles, progressIndicator);
		}
		return instance;
	}
	
	/**
	 * Open a tab witht he file specified
	 * @param paneFiles
	 * @param progress
	 * @param selectedFile
	 */
	public void openFile(File selectedFile) {		
		if(paneFiles!=null && selectedFile!=null){
			//Create the tab and the container
			Tab tab = new Tab(selectedFile.getName());
			final TextArea tarea = new TextArea();
			tab.setContent(tarea);
			paneFiles.getTabs().add(tab);
			//Add it to the map of files
			files.put(tab, selectedFile);
			//Call the service to fill the text
			ServiceFileText service = new ServiceFileText(selectedFile);
			progressIndicator.visibleProperty().bind(service.runningProperty());
			tarea.textProperty().bind(service.valueProperty());
			if(selectedFile.canWrite()){
				SimpleBooleanProperty finish = new SimpleBooleanProperty(true);
				finish.bind(service.runningProperty());
				finish.addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> arg0,
							Boolean oldValue, Boolean newValue) {
						if(!newValue){							
							tarea.textProperty().unbind();
						}
					}
				});
			}
			service.start();
		}
	}

	/**
	 * Creating a new file with an empty content
	 */
	public void createNewFile() {
		if(paneFiles!=null){
			//Create an empty tab
			Tab tab = new Tab("New "+counter ++);
			final TextArea tarea = new TextArea();
			tab.setContent(tarea);
			paneFiles.getTabs().add(tab);
		}		
	}

	/**
	 * Save the file currently selected
	 */
	public void saveFile() {
		File file = getFileSelected();
		if(file==null){
			saveAsFile();
		}else{
			saveFile(file);
		}
	}

	/**
	 * Save as the currently selected file
	 */
	public void saveAsFile() {
		File file = HelperEditorFiles.getSaveFile();
		saveFile(file);
	}

	/**
	 * Helper method that saves in the given file the selected content in the selected tab
	 * @param file
	 */
	private void saveFile(final File file) {
		if(file!=null){
			final ServiceFileSave service = new ServiceFileSave(file, getContentTabSelected());
			progressIndicator.visibleProperty().bind(service.runningProperty());
			service.start();
			SimpleBooleanProperty finish = new SimpleBooleanProperty(true);
			finish.bind(service.runningProperty());
			finish.addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> arg0,
						Boolean oldValue, Boolean newValue) {
					if(!newValue){							
						Boolean ok = service.getValue();
						Tab tab = paneFiles.getSelectionModel().getSelectedItem();
						if(ok && tab!=null){
							files.remove(tab);
							tab.setText(file.getName());
							files.put(tab, file);
						}
					}
				}
			});
		}
	}
	
	/**
	 * Retrieves the content of the tab selected
	 * @return
	 */
	private String getContentTabSelected() {
		String textContent = null;
		Tab tab = paneFiles.getSelectionModel().getSelectedItem();
		if(tab!=null){
			Node content = tab.getContent();
			if(content!=null && content instanceof TextArea){
				TextArea textArea = (TextArea) content;
				textContent = textArea.getText();
			}
		}
		return textContent;
	}

	/**
	 * Get the file of the current tab
	 * @return
	 */
	private File getFileSelected() {
		File selectedFile = null;
		Tab tab = paneFiles.getSelectionModel().getSelectedItem();
		if(tab!=null){
			selectedFile = files.get(tab);
		}
		return selectedFile;
	}

}

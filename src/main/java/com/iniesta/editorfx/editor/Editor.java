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
 * File created: 02/02/2013 at 02:25:53 by antonio
 */
package com.iniesta.editorfx.editor;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import com.iniesta.editorfx.editor.files.HelperEditorFiles;
import com.iniesta.layerfx.HandlingView;

/**
 * @author antonio
 *
 */
public class Editor implements Initializable, HandlingView{

	private Stage stage;
	
	@FXML
	private AnchorPane mainPane;
	@FXML
	private TreeView<File> treeViewFolder;
	@FXML
	private ProgressBar progressBar;
	
	public void initialize(URL arg0, ResourceBundle arg1) {		
		progressBar.setVisible(false);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Pane getMainPane() {		
		return mainPane;
	}
	
	@FXML
	void handleMenuItemExitAction(ActionEvent event){
		System.exit(0);
	}
	
	@FXML
	void handleMenuLoadFolderAction(ActionEvent event){		
		File f = HelperEditorFiles.getFolder(stage);		
		ServiceFolderLoading service = new ServiceFolderLoading(f);
		progressBar.visibleProperty().bind(service.runningProperty());		
		treeViewFolder.rootProperty().bind(service.valueProperty());
		service.start();
	}
	
	@FXML
	void handleMenuItemLoadFileAction(ActionEvent event){
		File f = HelperEditorFiles.getFile(stage);		
		ServiceFolderLoading service = new ServiceFolderLoading(f);
		progressBar.visibleProperty().bind(service.runningProperty());		
		treeViewFolder.rootProperty().bind(service.valueProperty());
		service.start();
	}
}
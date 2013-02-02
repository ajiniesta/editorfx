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

import java.net.URL;
import java.util.ResourceBundle;

import com.iniesta.layerfx.HandlingView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author antonio
 *
 */
public class Editor implements Initializable, HandlingView{

	@FXML
	private AnchorPane mainPane;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	public void setStage(Stage stage) {
		// TODO Auto-generated method stub
		
	}

	public Pane getMainPane() {		
		return mainPane;
	}

}

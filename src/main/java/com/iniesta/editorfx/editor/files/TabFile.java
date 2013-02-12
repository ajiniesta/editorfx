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
 * File created: 07/02/2013 at 19:02:40 by antonio
 */
package com.iniesta.editorfx.editor.files;

import java.io.File;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;

import com.iniesta.editorfx.editor.files.container.Container;
import com.iniesta.editorfx.editor.files.container.TextAreaContainer;

/**
 * @author antonio
 *
 */
public class TabFile {
	
	private boolean newFile;
	private File file;
	private String tabName;
	private Tab tab;
	private Container container;
	private ProgressIndicator progressIndicator;
	private ChangeNameListener changeTabName;

	public TabFile(File file, ProgressIndicator progressIndicator) {
		this.tabName = file.getName();
		this.tab = new Tab(tabName);		
		this.container = new TextAreaContainer();
		this.tab.setContent(container.getNode());
		this.progressIndicator = progressIndicator;
		this.newFile = false;
		this.file = file;
	}
	
	public TabFile(String tabName, ProgressIndicator progressIndicator) {
		this.tabName = tabName;
		this.newFile = true;
		this.tab = new Tab(tabName);
		this.container = new TextAreaContainer();
		this.tab.setContent(container.getNode());
		this.progressIndicator = progressIndicator;
	}
	
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}



	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}



	/**
	 * @return the progressIndicator
	 */
	public ProgressIndicator getProgressIndicator() {
		return progressIndicator;
	}



	/**
	 * @param progressIndicator the progressIndicator to set
	 */
	public void setProgressIndicator(ProgressIndicator progressIndicator) {
		this.progressIndicator = progressIndicator;
	}



	/**
	 * @return the newFile
	 */
	public boolean isNewFile() {
		return newFile;
	}

	/**
	 * @param newFile the newFile to set
	 */
	public void setNewFile(boolean newFile) {
		this.newFile = newFile;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return tabName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.tabName = fileName;
	}

	/**
	 * @return the tab
	 */
	public Tab getTab() {
		return tab;
	}

	/**
	 * @param tab the tab to set
	 */
	public void setTab(Tab tab) {
		this.tab = tab;
	}

	/**
	 * @return the container
	 */
	public Container getContainer() {
		return container;
	}

	/**
	 * @param container the container to set
	 */
	public void setContainer(Container container) {
		this.container = container;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((container == null) ? 0 : container.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result
				+ ((tabName == null) ? 0 : tabName.hashCode());
		result = prime * result + (newFile ? 1231 : 1237);
		result = prime
				* result
				+ ((progressIndicator == null) ? 0 : progressIndicator
						.hashCode());
		result = prime * result + ((tab == null) ? 0 : tab.hashCode());
		return result;
	}

	public void openFile() {
		//Call the service to fill the text
		ServiceFileText service = new ServiceFileText(file);
		progressIndicator.visibleProperty().bind(service.runningProperty());
		container.textProperty().bind(service.valueProperty());
		if(file.canWrite()){
			SimpleBooleanProperty finish = new SimpleBooleanProperty(true);
			finish.bind(service.runningProperty());
			finish.addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> arg0,
						Boolean oldValue, Boolean newValue) {
					if(!newValue){							
						container.textProperty().unbind();
					}
				}
			});
		}
		service.start();		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TabFile))
			return false;
		TabFile other = (TabFile) obj;
		if (container == null) {
			if (other.container != null)
				return false;
		} else if (!container.equals(other.container))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (tabName == null) {
			if (other.tabName != null)
				return false;
		} else if (!tabName.equals(other.tabName))
			return false;
		if (newFile != other.newFile)
			return false;
		if (progressIndicator == null) {
			if (other.progressIndicator != null)
				return false;
		} else if (!progressIndicator.equals(other.progressIndicator))
			return false;
		if (tab == null) {
			if (other.tab != null)
				return false;
		} else if (!tab.equals(other.tab))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TabFile [newFile=" + newFile + ", file=" + file + ", fileName="
				+ tabName + ", tab=" + tab + ", container=" + container
				+ ", progressIndicator=" + progressIndicator + "]";
	}

	public void saveFile() {
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
						changingName(ok, file);						
					}
				}
			});
		}
	}
	
	/**
	 * Changing the name of the Tab File
	 * @param file
	 */
	private void changingName(boolean changed, File file) {
		if(file!=null){
			String newName = file.getName();
			//First change the name of the tabs...
			if(changeTabName!=null){				
				changeTabName.change(changed, this, tabName, newName);
			}
			//Now the data of the TabFile
			this.tabName = newName;
			this.file = file;
			this.newFile = false;
		}
	}

	/**
	 * Retrieves the content of the tab selected
	 * @return
	 */
	private String getContentTabSelected() {
		String textContent = null;
		if(container!=null){
			textContent = container.getText();
		}
		return textContent;
	}

	public void setOnChangeTabName(ChangeNameListener changeTabNameListener) {
		this.changeTabName = changeTabNameListener;		
	}
}

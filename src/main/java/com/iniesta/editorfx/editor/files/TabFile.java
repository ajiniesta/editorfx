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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;

import com.iniesta.editorfx.editor.AceEditor;
import com.iniesta.editorfx.editor.UpdateNotification;
import com.iniesta.editorfx.editor.files.business.HelperEditorFiles;
import com.iniesta.editorfx.editor.files.business.ServiceFileSave;
import com.iniesta.editorfx.editor.files.business.ServiceFileText;
import com.iniesta.editorfx.editor.files.container.Container;

/**
 * @author antonio
 *
 */
public class TabFile {
	
	public enum FILE_SUPPORTED_TYPE {PLAIN_TEXT, FORMAT_TEXT, CSV}
	
	private boolean newFile;
	private File file;
	private String tabName;
	private Tab tab;
	private Container container;
	private UpdateNotification updateNotification;
	private ChangeNameListener changeTabName;

	public TabFile(File file, UpdateNotification updateNotification) {
		this.newFile = false;
		this.file = file;
		initialize(file.getName(), updateNotification);
	}
	
	public TabFile(String tabName, UpdateNotification updateNotification) {
		this.newFile = true;
		initialize(tabName, updateNotification);
	}
	
	private void initialize(String tabName,	UpdateNotification updateNotification) {
		this.tabName = file!=null?file.getName():"unsaved";
		this.tab = new Tab(tabName);
		this.tab.setClosable(true);
		this.container = new AceEditor(updateNotification);
		this.tab.setContent(container.getNode());		
		this.updateNotification = updateNotification;
		tab.setOnSelectionChanged(new EventHandler<Event>() {			
			public void handle(Event arg0) {
				updateNotificationStatus();
			}
		});
		
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

	public void openFile() {
		//Call the service to fill the text
		ServiceFileText service = new ServiceFileText(file);
		updateNotification.bind(service.runningProperty());
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
		updateNotificationStatus();
		service.start();		
	}

	/**
	 * 
	 */
	private void updateNotificationStatus() {
		if(container.isChanged()){
			updateNotification.getLabelStatus().setVisible(true);
			updateNotification.getLabelStatus().setText("Modified");
		}else{
			updateNotification.getLabelStatus().setVisible(false);
			updateNotification.getLabelStatus().setText("");
		}
		container.changedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> v,
					Boolean oldValue, Boolean newValue) {				
				if(newValue){
					updateNotification.getLabelStatus().setVisible(true);
					updateNotification.getLabelStatus().setText("Modified");
				}
			}
		});
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
			updateNotification.bind(service.runningProperty());
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((changeTabName == null) ? 0 : changeTabName.hashCode());
		result = prime * result
				+ ((container == null) ? 0 : container.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + (newFile ? 1231 : 1237);
		result = prime * result + ((tab == null) ? 0 : tab.hashCode());
		result = prime * result + ((tabName == null) ? 0 : tabName.hashCode());
		result = prime
				* result
				+ ((updateNotification == null) ? 0 : updateNotification
						.hashCode());
		return result;
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
		if (changeTabName == null) {
			if (other.changeTabName != null)
				return false;
		} else if (!changeTabName.equals(other.changeTabName))
			return false;
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
		if (newFile != other.newFile)
			return false;
		if (tab == null) {
			if (other.tab != null)
				return false;
		} else if (!tab.equals(other.tab))
			return false;
		if (tabName == null) {
			if (other.tabName != null)
				return false;
		} else if (!tabName.equals(other.tabName))
			return false;
		if (updateNotification == null) {
			if (other.updateNotification != null)
				return false;
		} else if (!updateNotification.equals(other.updateNotification))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TabFile [newFile=" + newFile + ", file=" + file + ", tabName="
				+ tabName + ", tab=" + tab + ", container=" + container
				+ ", updateNotification=" + updateNotification
				+ ", changeTabName=" + changeTabName + "]";
	}
}

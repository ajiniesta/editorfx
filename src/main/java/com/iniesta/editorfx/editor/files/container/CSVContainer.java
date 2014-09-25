package com.iniesta.editorfx.editor.files.container;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import com.iniesta.editorfx.editor.UpdateNotification;
import com.iniesta.editorfx.editor.files.business.ServiceFileText;

public class CSVContainer implements Container{

	private static final String DEFAULT_SEPARATOR = ";";
	private static final String DEFAULT_PREFIX_COL = "COL_";
	private TableView<ObservableList<String>> tableView;
	private SimpleStringProperty plainText;
	private SimpleBooleanProperty changed;
	private UpdateNotification notification;
	
	public CSVContainer(UpdateNotification notification){
		this(DEFAULT_SEPARATOR, notification);
	}
	
	public CSVContainer(final String separator, UpdateNotification notification){
		this.notification = notification;
		initializeTableView();
		plainText.addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
				ObservableList<ObservableList<String>> convertToMatrix = convertToMatrix(newValue, separator);
				initializeColumns(convertToMatrix);
			}
		});
	}

	private void initializeTableView() {
		tableView = new TableView<ObservableList<String>>();		
	}
	
	protected void initializeColumns(ObservableList<ObservableList<String>> matrix) {
		if(matrix!=null){			
			for (int index = 0; index<matrix.size(); index++) {
				final int finalIndex = index;
				String colName = DEFAULT_PREFIX_COL+(index+1);
				TableColumn< ObservableList<String>, String> col = new TableColumn<ObservableList<String>, String>(colName);
				col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<String>,String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> cdf) {
						return new SimpleStringProperty(cdf.getValue().get(finalIndex));
					}
				});
				tableView.getColumns().add(col);
			}			
		}		
	}

	protected ObservableList<ObservableList<String>> convertToMatrix(String text, String separator) {
		ObservableList<ObservableList<String>> items = null;
		if(text!=null && separator!=null){
			items = FXCollections.observableArrayList();
			String[] lines = text.split(ServiceFileText.LINE_SEPARATOR);
			if(lines!=null){
				for (int i = 0; i < lines.length; i++) {
					String[] line = lines[i].split(separator);
					if(line!=null){
						ObservableList<String> row = FXCollections.observableArrayList();
						for (int j = 0; j < line.length; j++) {
							row.add(line[j]);
						}
						items.add(row);
					}
				}
			}
		}
		return items;
	}

	public Node getNode() {
		return tableView;
	}

	public StringProperty textProperty() {
		return plainText;
	}

	public BooleanProperty changedProperty() {
		return changed;
	}

	public String getText() {
		return plainText.get();
	}

	public boolean isChanged() {
		return changed.get();
	}

}

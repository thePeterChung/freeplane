package org.freeplane.plugin.chat;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import org.freeplane.features.attribute.AttributeController;
import org.freeplane.features.attribute.NodeAttributeTableModel;
import org.freeplane.features.attribute.mindmapmode.AttributeUtilities;
import org.freeplane.features.filter.Filter;
import org.freeplane.features.filter.FilterConditionEditor;
import org.freeplane.features.filter.FilterController;
import org.freeplane.features.filter.condition.ASelectableCondition;
import org.freeplane.features.map.IMapSelection;
import org.freeplane.features.map.MapController;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.Controller;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ChatHelper {

    List<OfField> fromTheClass(Class<?> aClass) {
        // Get all declared fields of the class
        java.lang.reflect.Field[] fields = aClass.getDeclaredFields();
        List <OfField> inTheClass = new ArrayList<>();

        // Iterate over the fields add field name and class type
        for (java.lang.reflect.Field field : fields) {
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();
            OfField aField = new OfField();
            aField.name = fieldName;
            aField.type = fieldType;
            inTheClass.add(aField);
        }
        OfField error = new OfField();
        error.name = "error";
        Class<?> errorClassIs = "".getClass();
        error.type = errorClassIs;
        inTheClass.add(error);
        return inTheClass;
    }

    void addAttributeToNode(final NodeModel node, List<OfField> inTheClass) {
        final AttributeUtilities atrUtil = new AttributeUtilities();
        if (!atrUtil.hasAttributes(node)) {
            AttributeController.getController().createAttributeTableModel(node);
            final NodeAttributeTableModel natm = NodeAttributeTableModel.getModel(node);

            for (int i = 0; i < inTheClass.size(); i++) {
                    AttributeController.getController().performInsertRow(node, natm, i, inTheClass.get(i).name, "");
            }
        }
    }

    void addAttributeToNode(final NodeModel node, UserMessage msg) {
        final AttributeUtilities atrUtil = new AttributeUtilities();
        if (!atrUtil.hasAttributes(node)) {
            AttributeController.getController().createAttributeTableModel(node);
            final NodeAttributeTableModel natm = NodeAttributeTableModel.getModel(node);
            AttributeController.getController().performInsertRow(node, natm, 0, msg.getClass().getSimpleName(), msg.singleText());
        }
        else {
            final NodeAttributeTableModel natm = NodeAttributeTableModel.getModel(node);
            int lastRow = natm.getRowCount();
            AttributeController.getController().performInsertRow(node, natm, lastRow, msg.getClass().getSimpleName(), msg.singleText());
        }
    }

    void addAttributeToNode(final NodeModel node, AiMessage aiMsg) {
        final AttributeUtilities atrUtil = new AttributeUtilities();
        if (!atrUtil.hasAttributes(node)) {
            AttributeController.getController().createAttributeTableModel(node);
            final NodeAttributeTableModel natm = NodeAttributeTableModel.getModel(node);
            AttributeController.getController().performInsertRow(node, natm, 0, aiMsg.getClass().getSimpleName(), aiMsg.text());
        }
        else {
            final NodeAttributeTableModel natm = NodeAttributeTableModel.getModel(node);
            int lastRow = natm.getRowCount();
            AttributeController.getController().performInsertRow(node, natm, lastRow, aiMsg.getClass().getSimpleName(), aiMsg.text());
        }
    }

    NodeModel findNextNodeWithCondition(String condition) {
        final IMapSelection selection = Controller.getCurrentController().getSelection();
        final NodeModel start = selection.getSelected();
        NodeModel subtreeRoot = selection.getSelectionRoot();

        final FilterController filterController = FilterController.getCurrentFilterController();
        Filter filter = Controller.getCurrentController().getSelection().getFilter();
        FilterConditionEditor filterConditionEditor = new FilterConditionEditor(filterController, 5, FilterConditionEditor.Variant.SEARCH_DIALOG, new JPanel());
        filterConditionEditor.setFilter(1);//Core text
        filterConditionEditor.setEvaluation(2);//Is equal to
        filterConditionEditor.setCondition(condition);
        ASelectableCondition aSelectableCondition = filterConditionEditor.getCondition();

        final NodeModel next = filterController.findNextInSubtree(start, subtreeRoot, MapController.Direction.FORWARD, aSelectableCondition, filter);
        return next;
    }

    String getAttributeValueFromNode(final NodeModel node, EChatModelProp prop) {
        final AttributeUtilities atrUtil = new AttributeUtilities();
        String value = "";
        if (atrUtil.hasAttributes(node)) {
            final NodeAttributeTableModel natm = NodeAttributeTableModel.getModel(node);
            int index = natm.getAttributeIndex(prop.getAction());
            if (index >= 0) {
                value = natm.getValue(index).toString();
            }
        }
        return value;
    }

    Vector getAttributeTableFromNode(final NodeModel node) {
        final AttributeUtilities atrUtil = new AttributeUtilities();
        Vector vVector = new Vector();
        if (atrUtil.hasAttributes(node)) {
            final NodeAttributeTableModel natm = NodeAttributeTableModel.getModel(node);
            vVector = natm.getAttributes();
        }
        return vVector;
    }

    void setAttributeValue(final NodeModel node, EChatModelProp prop, String attributeValue) {
        final AttributeUtilities atrUtil = new AttributeUtilities();

        if (atrUtil.hasAttributes(node)) {
            final NodeAttributeTableModel natm = NodeAttributeTableModel.getModel(node);
            int index = natm.getAttributeIndex(prop.getAction());
            natm.setValue(node, index, attributeValue);
        }
    }
}

class OfField {
    String name;
    Class<?> type;
}
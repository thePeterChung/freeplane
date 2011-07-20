package org.freeplane.plugin.workspace.config.node;

public abstract class ConfigurationNode {
	private String id;
	private String name;
	
	public ConfigurationNode(String id) {
		this.id=id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String toString() {
		return this.getClass().getSimpleName()+"[id="+this.getId()+";name="+this.getName()+"]";
	}
}

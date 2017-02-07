package com.example.wordcount;

/**
 *
 * @author bshaw
 */
public interface MaxTreeNode {
    
    /**
     * Obtains the parent node.
     * @return Parent node, or null if this node is the head of the tree.
     */
    public MaxTreeNode getParent();
    
    
    /**
     * Obtains the child which is considered to be greater.
     * Note that it's legal for both children to have the same value.
     * @return Greater child node, or null if this branch is populated by a leaf.
     */
    public MaxTreeNode getGreaterChild();
    
    
    /**
     * Obtains the child which is considered to be lesser.
     * Note that it's legal for both children to have the same value.
     * @return Lesser child node, or null if this branch is populated by a leaf.
     */
    public MaxTreeNode getLesserChild();
    
    
    /**
     * Add a new node to the tree.
     * 
     * @param newnode 
     */
    public void insert(MaxTreeNode newnode);
}

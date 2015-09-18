package com.codekitchen.allen.mycce.events;

/**
 * An event to notify system which item the user selected.
 * @author Cloud
 */
public class ProductListingItemClickedEvent {

    String sequenceId;

    /**
     * Constructor of {@link ProductListingItemClickedEvent}.
     * @param sequenceId the id of selected line item
     */
    public ProductListingItemClickedEvent(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    /**
     * Get the sequence id of the selected item.
     * @return <code>String</code> of sequence id
     */
    public String getSequenceId() {
        return sequenceId;
    }
}

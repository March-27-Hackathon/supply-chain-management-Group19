/**
 * @author Tyler Tran <a href="mailto:tyler.tran3@ucalgary.ca"> tyler.tran3@ucalgary.ca</a>
 * @version 1.0
 * @since 1.0
*/

/**
 * Furniture class is an abstract class which contains fields and methods relavant
 * to all specific child furniture classes.
 */
package edu.ucalgary.ensf409;

public abstract class Furniture
{
    //--CLASS FIELDS--
    private String id;
    private String type;
    private String manuId;
    private int price;

    //--CLASS METHODS--

    // GETTER METHODS
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getManuId() {
        return manuId;
    }

    public int getPrice() {
        return price;
    }

    // SETTER METHODS
    public void setId(String id) {
        this.id = id;
    }

    public void setManuId(String manuId) {
        this.manuId = manuId;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

}
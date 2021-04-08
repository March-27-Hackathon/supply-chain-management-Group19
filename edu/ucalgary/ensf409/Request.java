/**
 * @author Dat Lam <a href="mailto:dat.lam1@ucalgary.ca">dat.lam1@ucalgary.ca</a>
 * @version 1.4
 * @since 1.1
 * 
 */

package edu.ucalgary.ensf409;

import java.util.ArrayList;

public class Request {

    private Storage storage;

    public Request(Storage storage)
    {
        this.storage = storage;
    }

    /**
     * Calculate posible combination of furniture that would make a complete
     * furniture of the type, then return the array list of those furnitures.
     * @param furniture
     * @param type
     * @return Array list of type object that extend from furniture
     */
    public ArrayList<? extends Furniture> request(String furniture, String type, int quantity)
    {
        switch(furniture.toLowerCase())
        {
            case "chair":
                //List only contain chair of that type
                return requestChair(storage.getChairStorage(type), quantity);
            case "desk":
                //List only contain desk of that type
                ArrayList<Desk> deskList = storage.getDeskStorage(type);
                ArrayList<Desk> returnDesk = new ArrayList<Desk>();
                while(quantity != 0)
                {
                    ArrayList<Desk> arr = requestDesk(deskList);
                    if(arr == null)
                    {
                        return null;
                    }
                    else
                    {
                        for(Desk desk : arr)
                        {
                            returnDesk.add(desk);
                            deskList.remove(desk);
                        }
                    }
                    quantity --;
                }
                return returnDesk;
            case "filing":
                //List only contain filing of that type
                ArrayList<Filing> filingList = storage.getFilingStorage(type);
                ArrayList<Filing> returnFiling = new ArrayList<Filing>();
                while(quantity != 0)
                {
                    ArrayList<Filing> arr = requestFiling(filingList);
                    if(arr == null)
                    {
                        return null;
                    }
                    else
                    {
                        for(Filing filing : arr)
                        {
                            returnFiling.add(filing);
                            filingList.remove(filing);
                        }
                    }
                    quantity --;
                }
                return returnFiling;
            case "lamp":
                //List only contain desk of that type
                ArrayList<Lamp> lampList = storage.getLampStorage();
                ArrayList<Lamp> returnLamp = new ArrayList<Lamp>();
                while(quantity != 0)
                {
                    ArrayList<Lamp> arr = requestLamp(lampList);
                    if(arr == null)
                    {
                        return null;
                    }
                    else
                    {
                        for(Lamp lamp : arr)
                        {
                            returnLamp.add(lamp);
                            lampList.remove(lamp);
                        }
                    }
                    quantity --;
                }
                return returnLamp;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Calculate and returnt the combined price of the items in the paramater
     * @param furnitures
     * @return price of all the item in the ArrayList
     */
    private static int priceOf(ArrayList<? extends Furniture> furnitures)
    {
        int total = 0;
        for(Furniture c: furnitures)
        {
            total += c.getPrice();
        }
        return total;
    }

    /**
     * Check wether or not the combination of chair will make a valid chair
     * @param chairs
     * @return if the combination of chair can make a valid chair
     */
    private boolean checkChairs(ArrayList<Chair> chairs)
    {
        boolean legs = false, arms = false, 
            seat = false, cushion = false;
        for(Chair c: chairs)
        {
            legs = legs || c.getLegs();
            arms = arms || c.getArms();
            cushion = cushion || c.getCushion();
            seat = seat || c.getSeat();
        }

        return legs && arms && seat && cushion;
    }

    private void extraChairParts(ArrayList<Chair> toReturn, ArrayList<Chair> pool)
    {
        boolean hasCushion = false, hasLegs = false, hasSeat = false, hasArms = false;
        for(Chair chair : toReturn)
        {
            if(chair.getLegs())
            {
                if(hasLegs)
                {
                    pool.add(new Chair(true, false, false, false, "remove", chair.getType(), "", 0));
                }
                else
                {
                    hasLegs = true;
                }
            }

            if(chair.getArms())
            {
                if(hasArms)
                {
                    pool.add(new Chair(false, true, false, false, "remove", chair.getType(), "", 0));
                }
                else
                {
                    hasArms = true;
                }
            }

            if(chair.getSeat())
            {
                if(hasSeat)
                {
                    pool.add(new Chair(false, false, true, false, "remove", chair.getType(), "", 0));
                }
                else
                {
                    hasSeat = true;
                }
            }

            if(chair.getCushion())
            {
                if(hasCushion)
                {
                    pool.add(new Chair(false, false, false, true, "remove", chair.getType(), "", 0));
                }
                else
                {
                    hasCushion = true;
                }
            }
        }

    }
    
    /**
     * Make a a request for the lowest price combination of chairs
     * using the list of chairs provided. 
     * @param list
     * @return ArrayList of combination of chair that would make a new
     * chair at the lowest price. null if request cannont be fulfill
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Chair> requestChair(ArrayList<Chair> list, int quantity)
    {
        ArrayList<ArrayList<Chair>> correctList = new ArrayList<ArrayList<Chair>>();
        ArrayList<Chair> temp1, temp2;
        for(int i = 0; i < list.size(); i ++)
        {
            temp1 = new ArrayList<Chair>();
            temp1.add(list.get(i));
            if(checkChairs(temp1))
            {
                correctList.add((ArrayList<Chair>)temp1.clone());
            }
            for(int j = i+1; j < list.size(); j ++)
            {
                temp2 = (ArrayList<Chair>)temp1.clone();
                for(int n = 0; n+j < list.size(); n ++)
                {
                    temp2.add(list.get(j+n));
                    if(checkChairs(temp2))
                    {
                        correctList.add((ArrayList<Chair>)temp2.clone());
                        temp2.remove(temp2.size()-1);
                    }
                }
            }
        }

        if(correctList.size() == 0)
        {
            return null;
        }
        int lowest = priceOf(correctList.get(0));
        ArrayList<Chair> lowestChairs = correctList.get(0);
        for(int i = 1; i < correctList.size(); i++)
        {
            if(lowest > priceOf(correctList.get(i)))
            {
                lowest = priceOf(correctList.get(i));
                lowestChairs = correctList.get(i);
            }   
        }
        return lowestChairs;
    }


    /**
     * Check wether the combination of desk in param desks
     * can be combine into a new desk.
     * @param desks
     * @return if the desks in list "desks" can make a complete desk.
     */
    private boolean checkDesks(ArrayList<Desk> desks)
    {
        boolean legs = false, top = false, 
            drawer = false;
        for(Desk d: desks)
        {
            legs = legs || d.getLegs();
            top = top || d.getTop();
            drawer = drawer || d.getDrawer();
        }

        return legs && top && drawer;
    }

    /**
     * Make a a request for the lowest price combination of desks
     * using the list of desks provided. return array list of
     * desk.
     * @param list
     * @return ArrayList of combination of desk with the lowest price that would make a new desk.
     * null if request cannont be fulfill
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Desk> requestDesk(ArrayList<Desk> list)
    {
        ArrayList<ArrayList<Desk>> correctList = new ArrayList<ArrayList<Desk>>();
        ArrayList<Desk> temp1, temp2;
        for(int i = 0; i < list.size(); i ++)
        {
            temp1 = new ArrayList<Desk>();
            temp1.add(list.get(i));
            if(checkDesks(temp1))
            {
                correctList.add((ArrayList<Desk>)temp1.clone());
            }
            for(int j = i+1; j < list.size(); j ++)
            {
                temp2 = (ArrayList<Desk>)temp1.clone();
                for(int n = 0; n + j < list.size(); n++)
                {
                    temp2.add(list.get(j+n));
                    if(checkDesks(temp2))
                    {
                        correctList.add((ArrayList<Desk>)temp2.clone());
                        temp2.remove(temp2.size()-1);
                    }
                }
            }
        }

        if(correctList.size() == 0)
        {
            return null;
        }
        int lowest = priceOf(correctList.get(0));
        ArrayList<Desk> lowestDesks = correctList.get(0);
        for(int i = 1; i < list.size(); i++)
        {
            if(lowest > priceOf(correctList.get(i)))
            {
                lowest = priceOf(correctList.get(i));
                lowestDesks = correctList.get(i);
            }   
        }

        return lowestDesks;
    }

    /**
     * Check wether the combination of desk in param filings
     * can be combine into a new filing.
     * @param filings
     * @return if the filings in  list "filings" can make a complete filing.
     */
    private boolean checkFilings(ArrayList<Filing> filings)
    {
        boolean rails = false, drawers = false, 
            cabinet = false;
        for(Filing f: filings)
        {
            rails = rails || f.getRails();
            cabinet = cabinet || f.getCabinet();
            drawers = drawers || f.getDrawers();
        }

        return rails && cabinet && drawers;
    }

    /**
     * Make a a request for the lowest price combination of filings
     * using the list of filings provided. 
     * @param list
     * @return ArrayList of combination of filing with the lowest price that would make a new filing.
     * null if request cannont be fulfill
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Filing> requestFiling(ArrayList<Filing> list)
    {
        ArrayList<ArrayList<Filing>> correctList = new ArrayList<ArrayList<Filing>>();
        ArrayList<Filing> temp1, temp2;
        for(int i = 0; i < list.size(); i ++)
        {
            temp1 = new ArrayList<Filing>();
            temp1.add(list.get(i));
            if(checkFilings(temp1))
            {
                correctList.add((ArrayList<Filing>)temp1.clone());
            }
            for(int j = i+1; j < list.size(); j ++)
            {
                temp2 = (ArrayList<Filing>)temp1.clone();
                for(int n = 0; n + j < list.size(); n++)
                {
                    temp2.add(list.get(j+n));
                    if(checkFilings(temp2))
                    {
                        correctList.add((ArrayList<Filing>)temp2.clone());
                        temp2.remove(temp2.size()-1);
                    }
                }
            }
        }

        if(correctList.size() == 0)
        {
            return null;
        }
        int lowest = priceOf(correctList.get(0));
        ArrayList<Filing> lowestFilings = correctList.get(0);
        for(int i = 1; i < list.size(); i++)
        {
            if(lowest > priceOf(correctList.get(i)))
            {
                lowest = priceOf(correctList.get(i));
                lowestFilings = correctList.get(i);
            }   

        }

        return lowestFilings;

    }
    
    /**
     * Check wether the combination of desk in param lamps
     * can be combine into a new lamps.
     * @param lamps
     * @return if the lamps in  list "lamps" can make a complete lamp.
     */
    private boolean checkLamps(ArrayList<Lamp> lamps)
    {
        boolean base = false, bulb = false;
        for(Lamp l: lamps)
        {
            base = base || l.getBase();
            bulb = bulb || l.getBulb();
        }

        return base && bulb;
    }

    /**
     * Make a a request for the lowest price combination of lamps
     * using the list of lamps provided. 
     * @param list
     * @return ArrayList of combination of lamp with the lowest price that would make a new lamp
     * null if request cannont be fulfill
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Lamp> requestLamp(ArrayList<Lamp> list)
    {
        ArrayList<ArrayList<Lamp>> correctList = new ArrayList<ArrayList<Lamp>>();
        ArrayList<Lamp> temp1, temp2;
        for(int i = 0; i < list.size(); i ++)
        {
            temp1 = new ArrayList<Lamp>();
            temp1.add(list.get(i));
            if(checkLamps(temp1))
            {
                correctList.add((ArrayList<Lamp>)temp1.clone());
            }
            for(int j = i+1; j < list.size(); j ++)
            {
                temp2 = (ArrayList<Lamp>)temp1.clone();
                for(int n = 0; n+j< list.size(); n++)
                {
                    temp2.add(list.get(j+n));
                    if(checkLamps(temp2))
                    {
                        correctList.add((ArrayList<Lamp>)temp2.clone());
                        temp2.remove(temp2.size()-1);
                    }
                }
            }
        }

        if(correctList.size() == 0)
        {
            return null;
        }
        int lowest = priceOf(correctList.get(0));
        ArrayList<Lamp> lowestLamps = correctList.get(0);
        for(int i = 1; i < list.size(); i++)
        {
            if(lowest > priceOf(correctList.get(i)))
            {
                lowest = priceOf(correctList.get(i));
                lowestLamps = correctList.get(i);
            }   
        }

        return lowestLamps;
    }
}

package atlas.kingj.roi;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ROIData implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	public double value = 0;  // profit value of material in £/tonne
	public double sellingprice = 0;
	public double contribution = 0;
	
	public double energycost = 0;
	
	public double wastesavedtable = 0;
	public double wastesavedflag =  0;
	public double wastesavedguide = 0;
	
	public List<EnergyData> energies;
	
	public List<MaintData> maintenance;
	
	public int[] selection;
	
	public Object clone(){
		ROIData out = new ROIData();
		out.value = value;
		out.sellingprice = sellingprice;
		out.contribution = contribution;
		out.energycost = energycost;
		out.wastesavedflag = wastesavedflag;
		out.wastesavedguide = wastesavedguide;
		out.wastesavedtable = wastesavedtable;
		out.energies = energies;
		out.maintenance = maintenance;
		out.selection = selection;
		return out;
	}

	public void setSize(int i){
		energies = new LinkedList<EnergyData>();
		for(int x = 0; x<i; x++)
			energies.add(new EnergyData());
		maintenance = new LinkedList<MaintData>();
		for(int x = 0; x<i; x++)
			maintenance.add(new MaintData());
	}
	
	public void reset(){
		selection = new int[0];
		//energies.clear();
		maintenance.clear();
	}
	
	public ROIData(){
		energies = new LinkedList<EnergyData>();
		maintenance = new LinkedList<MaintData>();
		selection = new int[0];
	}
	
	class EnergyData implements Serializable {
		private static final long serialVersionUID = 1L;
		public double kwhrsperyear;
		public int viewtype = 1; // 1 = avg power, 2 = hrly, 3 = annual
	}
	
	class MaintData implements Serializable {
		private static final long serialVersionUID = 1L;
		public double tothours;
		public double labourhourly;
		public double parts;
		public double totcost;
	}

}

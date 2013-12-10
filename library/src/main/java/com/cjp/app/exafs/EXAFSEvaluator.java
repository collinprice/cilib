package com.cjp.app.exafs;

import java.io.File;
import java.util.List;

import com.cjp.app.exafs.ifeffit.IFEFFITHelper;
import com.cjp.app.exafs.pdb.Atom;
import com.cjp.app.exafs.pdb.PDBHelper;
import com.cjp.io.MapParser;

public class EXAFSEvaluator {
	
	private IFEFFITHelper ifeffitHelper;
	private PDBHelper pdbHelper;
	
	private static EXAFSEvaluator exafsEvaluator;
	
	private EXAFSEvaluator(String configFile) {
		
		MapParser config = new MapParser(configFile);
		
		this.pdbHelper = new PDBHelper(config.getString("ifeffit-dir")+"/"+config.getString("amber"), config.getString("ifeffit-dir")+"/"+config.getString("pdb-file"), config.getList_String("atom-filter"));
		this.ifeffitHelper = new IFEFFITHelper(config, pdbHelper.getEXAFSAtoms());
	}
	
	public List<Double> getEXAFSAtoms() {
		return pdbHelper.getEXAFSAtomsAsList();
	}
	
	public static EXAFSEvaluator sharedInstance() {
		
		if (exafsEvaluator == null) {
			exafsEvaluator = new EXAFSEvaluator("config/ifeffit.cfg");
		}
		
		return exafsEvaluator;
	}
	
	public double evaluateAtoms(List<Double> input) {
		pdbHelper.setEXAFSAtomsFromList(input);
		ifeffitHelper.evaluate(pdbHelper.getEXAFSAtoms());
		
		return ifeffitHelper.getRMSD();
	}
}

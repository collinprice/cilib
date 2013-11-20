package com.cjp.app.exafs.pdb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PDBHelper {
	
	List<Atom> _atoms;
	
	public PDBHelper(String prmtop, String pdb) {
		
		List<Double> massList = new ArrayList<Double>();
		List<Atom> pointList = new ArrayList<Atom>();
		int numberOfAtoms = -1;
		
		try {
			BufferedReader prmtopFile = new BufferedReader(new FileReader(prmtop));
			
			String line;
			while((line = prmtopFile.readLine()) != null) {
				
				StringTokenizer tokenizer = new StringTokenizer(line, " %");
				
				if (tokenizer.hasMoreTokens() && tokenizer.nextToken().equalsIgnoreCase("FLAG")) {
					
					String flag = tokenizer.nextToken();
					if (flag.equalsIgnoreCase("POINTERS")) {
						prmtopFile.readLine();
						String pointersLine = prmtopFile.readLine();
						StringTokenizer pointersTokenizer = new StringTokenizer(pointersLine);
						numberOfAtoms = Integer.parseInt(pointersTokenizer.nextToken());
					} else if (flag.equalsIgnoreCase("MASS")) {
						prmtopFile.readLine();
						String massLine;
						while( massList.size() != numberOfAtoms && (massLine = prmtopFile.readLine()) != null) {
							
							StringTokenizer massTokenizer = new StringTokenizer(massLine);
							while (massTokenizer.hasMoreElements()) {
								
								massList.add(Double.parseDouble(massTokenizer.nextToken()));
							}
						}
					}
				}
			}
			
			prmtopFile.close();
			
			BufferedReader pdbFile = new BufferedReader(new FileReader(pdb));
			pdbFile.readLine(); // Skip line.
			
			while((line = pdbFile.readLine()) != null) {
				
				StringTokenizer tokenizer = new StringTokenizer(line);
				if (tokenizer.countTokens() == 1) break;
				
				// Skip a bunch
				tokenizer.nextToken();
				tokenizer.nextToken();
				tokenizer.nextToken();
				tokenizer.nextToken();
				tokenizer.nextToken();
				tokenizer.nextToken();
				
				Point3D point = new Point3D();
				point.x = Double.parseDouble(tokenizer.nextToken());
				point.y = Double.parseDouble(tokenizer.nextToken());
				point.z = Double.parseDouble(tokenizer.nextToken());
				
				pointList.add(new Atom(point, massToSymbol(massList.get(pointList.size())), Double.parseDouble(tokenizer.nextToken()) == 1));
			}
			pdbFile.close();
			
			_atoms = pointList;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Atom> getEXAFSAtoms() {
		
		List<Atom> exafsAtoms = new ArrayList<Atom>();
		for (Atom atom : _atoms) {
			if (atom.isEXAFS()) {
				exafsAtoms.add(atom);
			}
		}
		
		return exafsAtoms;
	}
	
	public List<Double> getEXAFSAtomsAsList() {
		
		List<Atom> atoms = getEXAFSAtoms();
		List<Double> convertedAtomsList = new ArrayList<Double>();
		
		for (Atom atom : atoms) {
			convertedAtomsList.add(atom.getPoint().x);
			convertedAtomsList.add(atom.getPoint().y);
			convertedAtomsList.add(atom.getPoint().z);
		}
		
		return convertedAtomsList;
	}
	
	public void setEXAFSAtomsFromList(List<Double> coordList) {
		
		if (coordList.size() == 0 || coordList.size() / 3 != getEXAFSAtoms().size()) {
			System.err.println("PDBHelper: Invalid atom list.");
			System.exit(0);
		}
		
		List<Atom> atoms = getEXAFSAtoms();
		for (int i = 0; i < coordList.size(); i += 3) {
			
			atoms.get(i/3).setPoint(new Point3D(coordList.get(i),coordList.get(i+1),coordList.get(i+2)));
		}
	}
	
	private String massToSymbol(double mass) {
		
		if (Math.rint(mass) == 1) {
			return "H";
		} else if (Math.rint(mass) == 12) {
			return "C";
		} else if (Math.rint(mass) == 14) {
			return "N";
		} else if (Math.rint(mass) == 16) {
			return "O";
		} else if (Math.rint(mass) == 23) {
			return "Na";
		} else if (Math.rint(mass) == 24) {
			return "Mg";
		} else if (Math.rint(mass) == 32) {
			return "S";
		} else if (Math.rint(mass) == 35) {
			return "Cl";
		} else if (Math.rint(mass) == 65) {
			return "Zn";
		} else if (Math.rint(mass) == 40) {
			return "Ca";
		} else if (mass > 54.99 && mass < 55.1) {
			return "Fe";
		} else if (mass > 54.5 && mass < 55) {
			return "Mn";
		}
		
		System.err.println("Missing mass...");
		return null;
	}
}

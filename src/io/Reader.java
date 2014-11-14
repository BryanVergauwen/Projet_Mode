package io;

import java.util.ArrayList;
import java.util.List;

import objects.Face;
import objects.Point;
import objects.Segment;

public abstract class Reader {
	protected List<Point> points = new ArrayList<Point>();
	protected List<Segment> segments = new ArrayList<Segment>();
	protected List<Face> faces = new ArrayList<Face>();
	protected int nbPoint, nbSegments, nbFaces;
	
	public List<Point> getListPoint() {
		return points;
	}

	public List<Segment> getListSegments() {
		return segments;
	}

	public List<Face> getListFaces() {
		return faces;
	}
}
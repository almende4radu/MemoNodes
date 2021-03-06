package com.chap.memo.memoNodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MemoServlet extends HttpServlet {

	@SuppressWarnings("unused")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String cleanDBParm = req.getParameter("cleanDB");
		if (cleanDBParm != null) {
			resp.setContentType("text/plain");
			resp.getWriter().println("Database cleared!");
			MemoShardStore.emptyDB();
		}
		if (cleanDBParm != null && cleanDBParm.equals("only")) {
			return;
		}
		String useDatastore = req.getParameter("useDatastore");
		if (useDatastore != null){
			NodeList.useDataStore();
		} else {
			NodeList.useMemory();
		}
		int nofNodes = 100000;
		String sNofNodes = req.getParameter("nofNodes");
		if (sNofNodes != null){
			try {
				nofNodes=Integer.parseInt(sNofNodes);
			} catch (Exception e){
				System.out.println("couldn't parse nofNodes="+sNofNodes);
			}
		}
		
		resp.setContentType("text/html");

		Date start = new Date();
		resp.getWriter()
				.println(
						"<html><body>Starting to generate Nodes<br><div style='display:block;position:relative;width:1000px;height:600px'>");

		// Generate 10.000 nodes in one linked list
		MemoNode node = new Unode(Node.storeAsChild("start", Node.store(Node.ROOT,"root")).child);
		for (int i = 0; i < nofNodes; i++) {
			node = Node.storeAsParent(new Integer(i).toString(), node).parent;
		}
		Date time = new Date();
		System.out.println("Storing done in "
				+ (time.getTime() - start.getTime()) + " ms -> "+ node.getId());
		resp.getWriter()
				.println(
						"<br>Done in:" + (time.getTime() - start.getTime())
								+ " ms<br>");
		int count = 0;
		// node = Node.find("start");
		while (node != null) {
			String value = node.getValue();
			//System.out.println(value+ ":" + node.getChildren().size());
			ArrayList<MemoNode> children = node.getChildren();
			if (children.isEmpty()) {
				System.out.println(node.getId() +":"+ node.getValue() + " has no children!");
				break;
			}
			count++;
			if (children.get(0) == null){
				System.out.println(node.getId()+":"+ node.getValue() + " has NUll node as a child!");
			}
			node = children.get(0);
		}
		resp.getWriter().println(
				"Count " + count + " counted in:"
						+ (new Date().getTime() - time.getTime()) + " ms<br>");
	}
}

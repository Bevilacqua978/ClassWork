import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class PagingSimulator {
	
	public static class Page {
		private int pnum;
		private int psize;
		private int pfirst;
		private int preq;
		public Page(int pn, int ps, int pf, int pr)
		{
			pnum = pn;
			psize = ps;
			pfirst = pf;
			preq = pr;
		}
		public boolean canReach(int page)
		{
			System.out.println("Check if " + page + " >= " + pfirst + " and < " + (pfirst+preq));
			return page < preq;
		}
		@Override
		public String toString()
		{
			return "p#" + pnum + "@" + pfirst + "=" + psize + "b(" + preq + "p)";
		}
	}
	
	public static void main(String [] args) throws IOException
	{
		//settings
		String inFile = "paging_input_2.txt";
		String outFile = "paging_output_2.txt";
		ArrayList<Page> pages = new ArrayList<Page>();

		//open input and output files.
		Scanner sc = new Scanner(new File(inFile));
		PrintWriter out = new PrintWriter(outFile);
		out.write("Input file: " + inFile + "\r\n");

		//get the page size
		int pageSize = sc.nextInt();		
		sc.nextLine();
		out.write("Page size is " + pageSize + "\r\n");
	
		//get process count
		int processCount = sc.nextInt();
		sc.nextLine();
		out.write("" + processCount + " processes created\r\n");
		
		//load all the process requests
		out.write("Process memory allocation:\r\n");
		out.write("PROCESS\t#PAGES\r\n");		
		int pagesAllocated = 0;
		for(int i=0;i<processCount;i++)
		{
			//get the process number & memory request size
			int pnum = sc.nextInt();
			int psize = sc.nextInt();
			sc.nextLine();

			int pagesNeeded = ((psize-1)/pageSize) + 1;
			out.write("" + pnum + "\t" + pagesNeeded + "\r\n");
			
			Page p = new Page(pnum, psize, pagesAllocated, pagesNeeded);
			pages.add(p);
			pagesAllocated = pagesAllocated + pagesNeeded;
		}
		System.out.println("All pages loaded");
		System.out.println(pages);
		out.write(sc.nextLine() + "\r\n"); //directly copy the text line		
		
		//process the rest of the lines
		out.write("Memory Accesses:\r\n");
		out.write("PROCESS\tP\tD\r\n");
		while(sc.hasNextLine())
		{
			int pnum = sc.nextInt();
			int paddr = sc.nextInt();
			int ppage = ((paddr-1)/pageSize)+1;
			if(sc.hasNextLine())
				sc.nextLine();
			System.out.println("Checking if page " + pnum + " has access to " + ppage);
			System.out.println(pages.get(pnum).canReach(ppage));
			System.out.println("Offset " + (paddr%512));
			if(pages.get(pnum).canReach(ppage))
				out.write("" + pnum + "\t" + ppage + "\t" + (paddr%512) + "\r\n");
			else
				out.write("" + pnum + "\t" + "ILLEGAL\r\n");
		}
		
		out.close();
		sc.close();
		System.out.println("Done");
	}
}

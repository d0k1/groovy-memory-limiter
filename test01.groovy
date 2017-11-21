
def a = new ArrayList();
int step = 0;
try{
	for(int i=0;i<100000;i++){
		a.add(new Long(100L));
		step = i;	
	}
} catch(Throwable t){
	println "Done ${step} iterations"
	throw t;
}
println a.size();

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

public class ID3 {
    String [] attributesNames;//array with Names of Attributes

    int[][]a; //o pinakas a[][]  //domain

    Vector<LinkedHashMap<String,Integer>> VectorApofaseis=new Vector<LinkedHashMap<String,Integer>>();

    ArrayList<Integer> SpamHam;
    ArrayList<Integer> sms;

    TreeNode root=new TreeNode();

    /*main function*/
    public static void main(String[] args)throws Exception{

        ID3 id3=new ID3();

    	id3.readfiles();
        id3.readData();

    	id3.createTree();
    	id3.readEmail();
    }

    public void createTree() {
        //ArrayList<ArrayList<Integer>> list=new ArrayList<ArrayList<Integer>>();
        LinkedHashMap<String,Integer> listApofasi=new LinkedHashMap<String,Integer>();
        ID3_Tree(root,listApofasi);

/*        for(int i=0;i<VectorApofaseis.size();i++){
            for (int j=0;j<VectorApofaseis.get(i).size();j++){
                for(int k=0; k<VectorApofaseis.get(i).get(j).size();k++){
                    System.out.print(VectorApofaseis.get(i).get(j).get(k)+",");
                }
                System.out.print(" ");
            }
            System.out.print("\n");
        }*/
    	

    }

    public void ID3_Tree(TreeNode treeNode,LinkedHashMap<String,Integer> listApofasi) {

        double bestIG=0;

        boolean boolSelect=false;
        int selectAttribute=0;

        treeNode.H_entropy=H(treeNode.ArrayRowID3,treeNode.ArrayColumnID3.get(treeNode.ArrayColumnID3.size()-1));
        //System.out.println("Entropia category= "+ treeNode.H_entropy);


        //System.out.println("Arraycolumn: " + treeNode.ArrayColumnID3.size());
        //System.out.println("ArrayRowID3: " + treeNode.ArrayRowID3.size());
        if((treeNode.ArrayColumnID3.size() == 2 && treeNode.ArrayRowID3.size() > 0) ){//|| treeNode.H_entropy==0){

            boolean flag;
            for(int i=0;i<=1;i++){
                flag=true;
                for(int k=0;k<treeNode.ArrayRowID3.size();k++){

                    if(a[treeNode.ArrayRowID3.get(k)][treeNode.ArrayColumnID3.get(0)] == i && a[treeNode.ArrayRowID3.get(k)][treeNode.ArrayColumnID3.get(1)] == -1){
                        for(int m=k;m<treeNode.ArrayRowID3.size();m++){
                            if(a[treeNode.ArrayRowID3.get(m)][treeNode.ArrayColumnID3.get(0)] == i && a[treeNode.ArrayRowID3.get(m)][treeNode.ArrayColumnID3.get(1)] == 1){
                                flag=false;
                                break;
                            }
                        }
                        if(flag){

                            LinkedHashMap<String,Integer> temphash = new LinkedHashMap<String,Integer>();

                            temphash.put(attributesNames[treeNode.ArrayColumnID3.get(0)],i);
                            //ArrayList<Integer> listStoixeio=new ArrayList<Integer>();
                            //listStoixeio.add(treeNode.ArrayColumnID3.get(0));
                            //listStoixeio.add(i);

                            temphash.put(attributesNames[treeNode.ArrayColumnID3.get(1)],a[treeNode.ArrayRowID3.get(k)][treeNode.ArrayColumnID3.get(1)]);
                            //listStoixeio.add(treeNode.ArrayColumnID3.get(1));
                            //listStoixeio.add(a[treeNode.ArrayRowID3.get(k)][treeNode.ArrayColumnID3.get(1)]);

                            //list.add(listStoixeio);
                            LinkedHashMap<String,Integer> finalhash = new LinkedHashMap<String,Integer>();
                            finalhash.putAll(listApofasi);
                            finalhash.putAll(temphash);
                            System.out.println("finalhash: " + finalhash);

                            VectorApofaseis.addElement(finalhash);
                            break;
                        }else{
                            break;
                        }

                    }else if(a[treeNode.ArrayRowID3.get(k)][treeNode.ArrayColumnID3.get(0)] == i && a[treeNode.ArrayRowID3.get(k)][treeNode.ArrayColumnID3.get(1)] == 1){
                        for(int m=k;m<treeNode.ArrayRowID3.size();m++){
                            if(a[treeNode.ArrayRowID3.get(m)][treeNode.ArrayColumnID3.get(0)] == i && a[treeNode.ArrayRowID3.get(m)][treeNode.ArrayColumnID3.get(1)] == -1){
                                flag=false;
                                break;
                            }
                        }
                        if(flag){
                            System.out.println("==1:!!!");
                            LinkedHashMap<String,Integer> temphash = new LinkedHashMap<String,Integer>();

                            temphash.put(attributesNames[treeNode.ArrayColumnID3.get(0)],i);
                            //ArrayList<Integer> listStoixeio=new ArrayList<Integer>();
                            //listStoixeio.add(treeNode.ArrayColumnID3.get(0));
                            //listStoixeio.add(i);

                            temphash.put(attributesNames[treeNode.ArrayColumnID3.get(1)],a[treeNode.ArrayRowID3.get(k)][treeNode.ArrayColumnID3.get(1)]);
                            //listStoixeio.add(treeNode.ArrayColumnID3.get(1));
                            //listStoixeio.add(a[treeNode.ArrayRowID3.get(k)][treeNode.ArrayColumnID3.get(1)]);

                            //list.add(listStoixeio);
                            LinkedHashMap<String,Integer> finalhash = new LinkedHashMap<String,Integer>();
                            finalhash.putAll(listApofasi);
                            finalhash.putAll(temphash);
                            System.out.println("finalhash: " + finalhash);

                            VectorApofaseis.addElement(finalhash);
                            break;
                        }else{
                            break;
                        }

                    }

                }
            }

            return;
        }else if (treeNode.H_entropy == 0){

            for (int i=0;i<treeNode.ArrayRowID3.size();i++){
                LinkedHashMap<String,Integer> temphash = new LinkedHashMap<String,Integer>();
                for ( int j =0; j < treeNode.ArrayColumnID3.size();j++){
                    temphash.put(attributesNames[treeNode.ArrayColumnID3.get(j)],a[treeNode.ArrayRowID3.get(i)][treeNode.ArrayColumnID3.get(j)]);
                }
                LinkedHashMap<String,Integer> finalhash = new LinkedHashMap<String,Integer>();
                finalhash.putAll(listApofasi);
                finalhash.putAll(temphash);
                VectorApofaseis.add(finalhash);
            }


            return;
        }
        else {

            //number of Attributes //ArrayColumnID3-1 giati afairoume to category
            //first X 8esi 0,second Y 8esi 1 ,ktl.

            /////////////////////////////////////////////////////
            //INFORMATION GAIN
            for (int i = 0; i < treeNode.ArrayColumnID3.size() - 1; i++) {

                //ean gurisei true exei paidi paei sto i++ //eno an false(den exei patera)sunexizei
                //if ( alreadyUsedToDecompose(treeNode, treeNode.ArrayColumnID3.get(i)) ){
                //  continue;
                //}

                //start IG
                double InformationGain = 0;

                ArrayList<Integer> smsCounter = new ArrayList<Integer>();
                for (int s = 0; s < sms.size(); s++) {
                    smsCounter.add(0);
                }
                smsCounter = itemsCount(treeNode.ArrayRowID3, treeNode.ArrayColumnID3.get(i), smsCounter, sms);

                InformationGain = IG(treeNode.ArrayRowID3, treeNode.ArrayColumnID3.get(i), smsCounter, treeNode.H_entropy);
               // System.out.println("Information Gain: " + InformationGain);

                if (boolSelect == false) {
                    boolSelect = true;

                    bestIG = InformationGain;
                    selectAttribute = treeNode.ArrayColumnID3.get(i);

                } else {

                    if (InformationGain > bestIG) {
                        boolSelect = true;

                        bestIG = InformationGain;
                        selectAttribute = treeNode.ArrayColumnID3.get(i);

                    }
                }

            }
            //System.out.println("Best IG: " + bestIG + " , attribute: " + selectAttribute + " ,with name: " + attributesNames[selectAttribute]);

            if(bestIG==0){
                return;
            }

            treeNode.decompositionAttribute= selectAttribute;

            //INFORMATION GAIN
            //////////////////////

            treeNode.decompositionValue = 0;

            if ( treeNode.decompositionValue == 0){

                listApofasi.put(attributesNames[treeNode.decompositionAttribute],treeNode.decompositionValue);

                //System.out.println(treeNode.ArrayColumnID3 + " Column Left");
                ArrayList<Integer> arrayColtemp = new ArrayList<Integer>();
                for (int i = 0; i < treeNode.ArrayColumnID3.size(); i++) {

                    if (treeNode.ArrayColumnID3.get(i) != selectAttribute) {
                        arrayColtemp.add(treeNode.ArrayColumnID3.get(i));

                    }
                }
                //System.out.println(treeNode.ArrayRowID3+ " Left");
                ArrayList<Integer> arrayRowtemp = new ArrayList<Integer>();
                for (int i = 0; i < treeNode.ArrayRowID3.size(); i++) {
                    //System.out.println("a["+treeNode.ArrayRowID3.get(i)+"]["+selectAttribute+"]: "+ a[treeNode.ArrayRowID3.get(i)][selectAttribute]);
                    if (a[treeNode.ArrayRowID3.get(i)][selectAttribute] == 0) {
                        arrayRowtemp.add(treeNode.ArrayRowID3.get(i));
                    }
                }


                //System.out.println("Arraylist to go Left: " + arrayRowtemp);
                TreeNode newTree = new TreeNode(arrayRowtemp, arrayColtemp);

                ID3_Tree(newTree,listApofasi);
                //System.out.println("heee1");
                treeNode.decompositionValue = 1;
            }
            if(treeNode.decompositionValue==1){
                //System.out.println("heee2");
                listApofasi.put(attributesNames[treeNode.decompositionAttribute],treeNode.decompositionValue);


                //System.out.println(treeNode.ArrayColumnID3 + " Column Right");
                ArrayList<Integer> arrayColtemp = new ArrayList<Integer>();
               for (int i = 0; i < treeNode.ArrayColumnID3.size(); i++) {

                    if (treeNode.ArrayColumnID3.get(i) != selectAttribute) {
                        arrayColtemp.add(treeNode.ArrayColumnID3.get(i));

                    }
                }
                //System.out.println(treeNode.ArrayRowID3 + " Right");
                ArrayList<Integer> arrayRowtemp = new ArrayList<Integer>();
                
                for (int i = 0; i < treeNode.ArrayRowID3.size(); i++) {
                   // System.out.println("a["+treeNode.ArrayRowID3.get(i)+"]["+selectAttribute+"]: "+ a[treeNode.ArrayRowID3.get(i)][selectAttribute]);
                    if (a[treeNode.ArrayRowID3.get(i)][selectAttribute] == 1) {
                        arrayRowtemp.add(treeNode.ArrayRowID3.get(i));
                    }
                }

                //System.out.println("Arraylist to go Right: " + arrayRowtemp);
                TreeNode newTree = new TreeNode(arrayRowtemp, arrayColtemp);

                ID3_Tree(newTree,listApofasi);
            }
            return;

        }





       /* if(treeNode.left==null) {
            ///ftiaxno left
            treeNode.decompositionValue = 0;

            ArrayList<Integer> listStoixeio=new ArrayList<Integer>();
            listStoixeio.add(treeNode.decompositionAttribute);
            listStoixeio.add(treeNode.decompositionValue);

            list.add(listStoixeio);


            for (int i = 0; i < treeNode.ArrayColumnID3.size(); i++) {
                if (treeNode.ArrayColumnID3.get(i) == selectAttribute) {
                    treeNode.ArrayColumnID3.remove(i);
                }
            }

            for (int i = 0; i < treeNode.ArrayRowID3.size(); i++) {
                if (a[treeNode.ArrayRowID3.get(i)][selectAttribute] == treeNode.decompositionValue) {
                    treeNode.ArrayRowID3.remove(i);
                }
            }
            TreeNode newTree = new TreeNode(treeNode.ArrayRowID3, treeNode.ArrayColumnID3);
            treeNode.left = newTree;
            ID3_Tree(newTree,list);
            ///ftiaxno left
        }*/
        /*if(treeNode.right==null) {
            ///ftiaxno right
            treeNode.decompositionValue = 1;

            ArrayList<Integer> listStoixeio=new ArrayList<Integer>();
            listStoixeio.add(treeNode.decompositionAttribute);
            listStoixeio.add(treeNode.decompositionValue);

            list.add(listStoixeio);

            for (int i = 0; i < treeNode.ArrayColumnID3.size(); i++) {
                if (treeNode.ArrayColumnID3.get(i) == selectAttribute) {
                    treeNode.ArrayColumnID3.remove(i);
                }
            }

            for (int i = 0; i < treeNode.ArrayRowID3.size(); i++) {
                if (a[treeNode.ArrayRowID3.get(i)][selectAttribute] == treeNode.decompositionValue) {
                    treeNode.ArrayRowID3.remove(i);
                }
            }
            TreeNode newTree = new TreeNode(treeNode.ArrayRowID3, treeNode.ArrayColumnID3);
            treeNode.right = newTree;
            ID3_Tree(newTree,list);
        }
        if(treeNode.right!=null) {
            return;
        }*/

        /*// again telos code*/

    }
    class TreeNode{

        public double H_entropy;

        //public Vector data;
        ArrayList<Integer> ArrayColumnID3;
        ArrayList<Integer> ArrayRowID3;

        int decompositionAttribute; //allagei onomastos??????????????????????

        int decompositionValue;//allagei onomastos????????????

        TreeNode left;
        TreeNode right;



        TreeNode []children;

        TreeNode parent;



        //constructor
        public TreeNode() {
            ArrayColumnID3=new ArrayList();
            ArrayRowID3=new ArrayList();

            right = null;
            left = null;
            //data = new Vector();
        }

        public TreeNode(ArrayList<Integer> ArrayRowID3,ArrayList<Integer> ArrayColumnID3) {
            this.ArrayColumnID3=ArrayColumnID3;
            this.ArrayRowID3=ArrayRowID3;

            right = null;
            left = null;
            //data = new Vector();
        }

    }

    //!calulate Information Gain!
    //
    // IG(C,X)
    // X=arrayColumnX   (deixnei poia stili koitao,e.g. to 0 einai h proti stili X,to 1 einai h deuteri stili Y ktl)
    // C=arrayColumn    (deixnei tin katigoria-Category,teleutaia stili)

    public double IG(ArrayList<Integer> arrayRow,int arrayColumnX,ArrayList<Integer> smsCounter,double  H_entropyBasic){
        double InformationGain = 0;
        double Pstart=0;
        double Hstart=0;
        double IGAll=0;

        //first Y=0,second Y=1
        for (int j=0; j< sms.size(); j++) {

            Pstart=P(arrayRow,arrayColumnX,smsCounter,sms.get(j));
            //System.out.println("pstart:::: "+Pstart);

            //dhmiourgia subset=uposunolou

            ArrayList<Integer> arrayRowH= new ArrayList<Integer>();
            for(int k=0;k<arrayRow.size();k++){
                if(a[arrayRow.get(k)][arrayColumnX]==sms.get(j)){
                    arrayRowH.add(arrayRow.get(k));
                }
            }
            Hstart=H(arrayRowH,a[a.length-1].length-1);
            //System.out.println("Hstart:::: "+Hstart);

            IGAll=IGAll+(Pstart*Hstart);

        }

        //System.out.println("Basic: "+IGAll);
        InformationGain=H_entropyBasic-IGAll;
        return InformationGain;
    }

    //!calculate P(X=x)
    //X=arrayColumnX e.g. X,Y,Z,Category
    //x=x e.g. x=0,x=1...

    public double P(ArrayList<Integer> arrayRow,int arrayColumnX,ArrayList<Integer> smsCounter,int x){

        int P1=0;
        int P2=0;
        double PEnd;

        for(int i=0;i<smsCounter.size();i++){
            P1=P1+smsCounter.get(i);
        }
        //System.out.println("p1_sosto!!!!:::: "+P1);
        //System.out.println("x_sosto!!!!:::: "+x);

        for(int i=0;i<arrayRow.size();i++){
            if(a[arrayRow.get(i)][arrayColumnX]==x){
                //System.out.println("mesa:::: "+a[arrayRow.get(i)][arrayColumnX]);
                P2++;
            }
        }
        //System.out.println("p2:::: "+P2);
        PEnd=(double)P2/P1;
        //System.out.println("pend:::: "+PEnd);
        return PEnd;
    }



    public boolean alreadyUsedToDecompose(TreeNode treeNode, int attribute) {

        if (treeNode.children != null) { //an exei paidi

            if (treeNode.decompositionAttribute == attribute )

                return true;

        }

        if (treeNode.parent == null) return false;

        return alreadyUsedToDecompose(treeNode.parent, attribute);

    }


    /*!calulate Entropy!*/
    public double H(ArrayList<Integer> ArrayRow,int Column){

        if(ArrayRow.size()==0){
            return 0;
        }

        ArrayList<Integer> arrayCounter= new ArrayList<Integer>();
        for(int i=0;i<SpamHam.size();i++){ //arxikopoioisi counter me 0
            arrayCounter.add(0);
        }

        arrayCounter=itemsCount(ArrayRow,Column,arrayCounter,SpamHam);
        // System.out.println("counter in entropy= "+arrayCounter.get(0));

        double H_entropy=0;

        for(int i=0;i<SpamHam.size();i++){
            H_entropy= H_entropy+(double)(-1)*(H_entropyP(arrayCounter.get(i),ArrayRow));
        }

        return H_entropy;

    }

    //!calculate the P(C=c)*log2(P(C=c))
    public double H_entropyP(int HamSpam,ArrayList<Integer> ArrayRow){
        //System.out.println("HamSpam: "+HamSpam);
        //System.out.println("arrayRow.size(): "+ArrayRow.size());
        if(HamSpam!=0){
            double H_entropyP11=(double)HamSpam/(ArrayRow.size());
            //System.out.println("entropy_fail: "+H_entropyP11);
            double H_entropyP12=Math.log10(H_entropyP11) / Math.log10(2);
            double H_entropyP13=(H_entropyP11*H_entropyP12 );

            return H_entropyP13;
        }
        return 0.0;

    }

    //!Calculate how many element is same in category
    //  !ArrayList sms= is the different items
    //  !and
    //  !the ArrayList smsCounter count same items which belong to the category
    //  !start:smsCounter=(0,0),sms=(A,B)
    //  !(e.x. X = A A A A B B B A B
    //  !      i find count=5 items A in category X
    //  !      i find count=4 items B in category X
    //  !      =>       sms=(A,B) and
    //  !      =>smsCounter=(5,4))

    public ArrayList<Integer> itemsCount(ArrayList<Integer> ArrayRow,int Column,ArrayList<Integer> arrayCounter,ArrayList<Integer> ListSmsOrSpamHam){

        for(int i=0;i<ArrayRow.size();i++){
            for(int k=0;k<ListSmsOrSpamHam.size();k++){
                if(a[ArrayRow.get(i)][Column]==ListSmsOrSpamHam.get(k)) {
                    arrayCounter.set(k,arrayCounter.get(k) + 1);
                }
            }

        }
        return arrayCounter;

    }


    public void readData(){

    	/*
        attributesNames = new String[4];
        attributesNames[0]="love";
        attributesNames[1]="money";
        attributesNames[2]="the";
        attributesNames[3]="Category";

        a=new int[8][4];
        //arxikopoio to basiko pinaka
        a[0][0]=0;
        a[1][0]=0;
        a[2][0]=0;
        a[3][0]=1;
        a[4][0]=1;
        a[5][0]=0;
        a[6][0]=0;
        a[7][0]=0;

        a[0][1]=1;
        a[1][1]=1;
        a[2][1]=1;
        a[3][1]=0;
        a[4][1]=1;
        a[5][1]=0;
        a[6][1]=0;
        a[7][1]=0;

        a[0][2]=0;
        a[1][2]=1;
        a[2][2]=0;
        a[3][2]=1;
        a[4][2]=0;
        a[5][2]=1;
        a[6][2]=0;
        a[7][2]=1;

        a[0][3]=1;
        a[1][3]=1;
        a[2][3]=1;
        a[3][3]=1;
        a[4][3]=-1;
        a[5][3]=-1;
        a[6][3]=-1;
        a[7][3]=-1;

		*/
        //pernao olo ton pinaka sto pinaka data tou TreeNode
        //arxikopoio pinaka TreeNode
        boolean flag=false;
        for(int i=0;i< a.length; ++i){
            root.ArrayRowID3.add(i);
            if(!flag){
                for(int j = 0; j < a[i].length; ++j) {
                    root.ArrayColumnID3.add(j);
                }
                flag=true;
            }
        }

        SpamHam=new ArrayList<Integer>();
        //Arxikopoio me tis dunates times pou mporei na exei to category
        SpamHam.add(-1);
        SpamHam.add(1);

        sms=new ArrayList<Integer>();
        //Arxikopoio me tis dunates times pou mporei na exei to xaraktiristiko
        sms.add(0);
        sms.add(1);
    }

    public void readfiles() throws FileNotFoundException {



        long start1 = System.currentTimeMillis();
        int i = 1;
        //String startname = "part1/spm";
        int numberOfSpam = 0;
        int numberOfHam = 0;

        Map<String, Integer> distinctWordCount = new TreeMap<String, Integer>();
        File dir = new File("datasets");
        Map<String, Integer> wordCountHam = new TreeMap<String, Integer>();
        if(!dir.isDirectory()) throw new IllegalStateException("Not a directory");
        for(File file : dir.listFiles()) {
            if(!file.getName().startsWith("spm")) {
                numberOfHam++;
                Scanner input = new Scanner(file);
                while (input.hasNext()) {
                    String next = input.next().toLowerCase();
                    if (!wordCountHam.containsKey(next)) {
                        wordCountHam.put(next, 1);
                        distinctWordCount.put(next, 1);
                    } else {
                        wordCountHam.put(next, wordCountHam.get(next) + 1);
                        distinctWordCount.put(next, distinctWordCount.get(next) + 1);
                    }

                }
                input.close();
            }
        }
        
        long start2 = System.currentTimeMillis();
        System.out.println("Time to read HamFiles:  " + (double)(start2 - start1) / 1000 + " sec.");

        Map<String, Integer> wordCountSpam = new TreeMap<String, Integer>();
        if(!dir.isDirectory()) throw new IllegalStateException("Not a directory");
        for(File file : dir.listFiles()) {
            if(file.getName().startsWith("spm")) {
                numberOfSpam++;
                Scanner input = new Scanner(file);
                while (input.hasNext()) {
                    String next = input.next().toLowerCase();
                    if (!wordCountSpam.containsKey(next)) {
                        wordCountSpam.put(next, 1);
                    } else {
                        wordCountSpam.put(next, wordCountSpam.get(next) + 1);
                    }
                    if ( !distinctWordCount.containsKey(next)) {
                        distinctWordCount.put(next, 1);
                    }else {
                        distinctWordCount.put(next, distinctWordCount.get(next) + 1);
                    }
                }
                input.close();
            }

        }



        long start3 = System.currentTimeMillis();
        System.out.println("Time to read SpamFiles:  " + (double)(start3 - start2) / 1000 + " sec.");

//        System.out.println(distinctWordCount);
        ///////////////////////////////////
        attributesNames = new String[distinctWordCount.size()+1];
        attributesNames[attributesNames.length-1]="category1";

        int kl = 0;
        for(Map.Entry<String,Integer> entry : distinctWordCount.entrySet()) {
            String key = entry.getKey();

            attributesNames[kl] = key;
            kl++;
        }
        attributesNames[attributesNames.length-1]="category1";
        a=new int[numberOfHam+numberOfSpam][distinctWordCount.size()+1];

        for(int z1 = 0; z1<numberOfHam+numberOfSpam;z1++){
            for(int z2=0; z2< distinctWordCount.size()+1;z2++){
                a[z1][z2] = 0;
            }
        }
        
        System.out.println("TELOS ARXIKOPOIHSHS");
        //File dir = new File("datasets");
        //Map<String, Integer> wordCountHam = new TreeMap<String, Integer>();
        if(!dir.isDirectory()) throw new IllegalStateException("Not a directory");
        int k =0;
        for(File file : dir.listFiles()) {
            if(!file.getName().startsWith("spm")) {
                numberOfHam++;
                Scanner input = new Scanner(file);
                a[k][distinctWordCount.size()] = 1;
                while (input.hasNext()) {
                    String next = input.next().toLowerCase();
                    for( int z1 = 0; z1 < attributesNames.length; z1++){
                        if(attributesNames[z1].equalsIgnoreCase(next)){
                            a[k][z1] = 1;
                            break;
                        }
                    }
                }
                k++;
                input.close();
            }

        }
        //long start2 = System.currentTimeMillis();
        //System.out.println("Time to read HamFiles:  " + (double)(start2 - start1) / 1000 + " sec.");
        System.out.println("TELOS APODOSHS TIMWN");
        //Map<String, Integer> wordCountSpam = new TreeMap<String, Integer>();
        if(!dir.isDirectory()) throw new IllegalStateException("Not a directory");

        for(File file : dir.listFiles()) {
            if(file.getName().startsWith("spm")) {
                numberOfSpam++;
                Scanner input = new Scanner(file);
                a[k][distinctWordCount.size()] = -1;
                while (input.hasNext()) {
                    String next = input.next().toLowerCase();
                    for( int z1 = 0; z1 < distinctWordCount.size()+1; z1++){
                        if(attributesNames[z1].equalsIgnoreCase(next)){
                            a[k][z1] = 1;
                            break;
                        }
                    }
                }
                k++;
                input.close();
            }
        }
        
        System.out.println("ARXH EKPAIDEYSHS");
        /*
        for (int z11 = 0; z11<attributesNames.length;z11++){
            System.out.print(attributesNames[z11]);
            System.out.print(" ");
        }

        for( int o1 = 0; o1 < 50; o1++){
            for ( int o2 = 0; o2 < 50;o2++){
                System.out.print(a[o1][o2]);
                System.out.print(" ");
            }
            System.out.println();
        }
        */
    }
    
    public void readEmail() throws FileNotFoundException {
    	
    	Map<String,Integer> unique = new LinkedHashMap<String,Integer>();

    	
    	int counter = 0;
    	int RealSpamCounter = 0;
		int RealHamCounter = 0;
    	File dir2 = new File("testing");
    	if(!dir2.isDirectory()) throw new IllegalStateException("Not a directory");
	    for(File file : dir2.listFiles()) {
	    	for ( int i = 0; i<attributesNames.length;i++) {
	    		unique.put(attributesNames[i],0);
	    	}
	    	boolean flag = false;
	    	if(file.getName().startsWith("spm")) {
	        	RealSpamCounter++;
	        }else {
	        	RealHamCounter++;
	        }
	    	
	    	Scanner input = new Scanner(file);
        	            
        	while (input.hasNext()) {
        		String next = input.next().toLowerCase();	
    			if ( unique.containsKey(next)) {
    				unique.put(next, 1);
    			}       		
        	}
        	counter++;
        	unique.put("category1", 1);
        	//System.out.println(unique);
        	for ( int j = 0; j < VectorApofaseis.size()-1; j++) {
        		if ( VectorApofaseis.get(j).equals(unique)) {
        			flag = true;
        		}
        	}
        	if ( flag ) {
            	System.out.println("Email number " + counter + " is Ham!");
        	}else {
        		//System.out.println("Email number " + counter + " is not Ham!");
        		//System.out.println("Checking for spam...");
        	}
        	flag = false;

        	unique.put("category1", -1);
        	//System.out.println(unique);
        	for ( int j = 0; j < VectorApofaseis.size()-1; j++) {
        		if ( VectorApofaseis.get(j).equals(unique)) {
        			flag = true;
        		}
        	}
        	if ( flag ) {
        		System.out.println("Email number " + counter + " is Spam!");
        	}
        	unique.clear();
	    }
    }


    /*treeNode.children=new TreeNode[sms.size()];

        for (int j=0; j< sms.size(); j++) {

            treeNode.children[j] = new TreeNode();

            treeNode.children[j].parent = treeNode;

            for(int c=0;c<treeNode.ArrayColumnID3.size();c++){
                if(treeNode.ArrayColumnID3.get(c)==treeNode.decompositionAttribute){
                    treeNode.children[j].ArrayColumnID3.remove(c);
                    break;
                }
            }
            for(int r=0;r<treeNode.ArrayRowID3.size();r++){
                if(a[treeNode.ArrayRowID3.get(r)][treeNode.decompositionAttribute]!=sms.get(j)){
                    treeNode.children[j].ArrayRowID3.remove(r);
                }
            }

            treeNode.children[j].decompositionValue = j;

        }



        for (int j=0; j< sms.size(); j++) {

            ID3_Tree(treeNode.children[j]);

        }

        treeNode.ArrayRowID3 = null;
        treeNode.ArrayColumnID3 = null;
        */

}

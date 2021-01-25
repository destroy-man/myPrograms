import java.util.*;

class UnluckyVassal {
    public void printReportForKing(List<String> pollResults) {
        //Определение родителей самого верхнего уровня
        List<String> mainParents=new ArrayList<String>();
        for(int i=0;i<pollResults.size();i++){
            String child=pollResults.get(i).split(":")[0];
            boolean isChild=false;
            for(int j=0;j<pollResults.size();j++) {
                if (i != j)
                    if (pollResults.get(j).contains(child))
                        isChild = true;
            }
            if(!isChild)
                mainParents.add(child);
        }
        //Определение потомков для каждого родителя
        Map<String,String[]> childs=new HashMap<String,String[]>();
        for(String result:pollResults)
            if(result.contains(":")){
                String parent=result.split(":")[0];
                String[] childsArray=result.split(":")[1].split(",");
                childs.put(parent,childsArray);
            }
        //Формирование иерархии
        System.out.println("Король");
        Collections.sort(mainParents);
        for(String parent:mainParents){
            int countParents=1;
            getChilds(parent,childs,countParents);
        }
    }

    //Получение наследников для конкретного родителя
    public void getChilds(String parent,Map<String,String[]> childs,int countParents){
        String[] childsArray=childs.get(parent);
        //Определение есть ли у родителя наследники
        if(childsArray!=null){
            Arrays.sort(childsArray);
            StringBuilder tab=new StringBuilder("");
            for(int i=0;i<countParents;i++)
                tab.append("\t");
            System.out.println(tab.toString() + parent);
            //Получение наследников у наследника
            for(String childElement:childsArray){
                if(childs.containsKey(childElement.trim())) {
                    countParents++;
                    getChilds(childElement.trim(), childs, countParents);
                    countParents--;
                }
                else{
                    countParents++;
                    tab=new StringBuilder("");
                    for(int i=0;i<countParents;i++)
                        tab.append("\t");
                    System.out.println(tab.toString() + childElement.trim());
                    countParents--;
                }
            }
        }
        else {
            StringBuilder tab=new StringBuilder("");
            for(int i=0;i<countParents;i++)
                tab.append("\t");
            System.out.println(tab.toString() + parent);
        }
    }
}

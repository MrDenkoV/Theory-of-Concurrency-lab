public class minList {
    Object val;
    minList next;
    boolean locked=false;

    public minList(Object value){
        assert value!=null;
        this.val=value;
        this.next=null;
    }

    public minList(Object value, minList next){
        assert value!=null;
        this.val=value;
        this.next=next;
    }

    public static void main(String[] args){
        
    }

    public static void writer(int times, int fi, minList list){
        for(int i=0; i<times; i++){
            list.add(i + fi);
        }
    }

    public static void reader(int times, int fi, minList list){
        for(int i=0; i<times; i++){
            if(i%2==0)
                list.contains(fi+times);
            else
                list.remove(fi+times);
        }
    }

    private void lock(){
        while(locked){
            try {
                Thread.sleep(100);
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("locking");
                System.exit(1);
            }
        }
        locked=true;
    }

    private void lockNext(){
        if(this.next!=null)
            this.next.lock();
    }

    private void unlock(){
        assert locked;
        locked=false;
    }

    private void unlockNext(){
        if(this.next!=null)
            this.next.unlock();
    }

    public boolean contains(Object o){
        if(!locked){
            this.lock();
        }
        this.lockNext();
        boolean res = o==val;
        this.unlock();
        if(res) {
            this.unlockNext();
            return true;
        }
        else if(this.next!=null) return this.next.contains(o);
        else return false;
    }

    public boolean remove(Object o){
        if(!locked){
            this.lock();
        }
        this.lockNext();
        if(this.val == o){
            if(this.next!=null){
                this.val=this.next.val;
                this.next.lockNext();
                this.next=this.next.next;
            }
            else{
                this.val=null;
                this.next=null;
            }
            this.unlockNext();
            this.unlock();
            return true;
        }
        else if(this.next==null) {
            this.unlockNext();
            this.unlock();
            return false;
        }
        else{
            if(this.next.val==o){
                this.next.lockNext();
                this.next=this.next.next;
                this.unlockNext();
                this.unlock();
                return true;
            }
            else {
                this.unlock();
                return this.next.remove(o);
            }
        }
    }

    public boolean add(Object o){
        if(!locked){
            this.lock();
        }
        this.lockNext();
        if(this.next!=null){
            this.unlock();
            return this.next.add(o);
        }
        else{
            this.next= new minList(o);
            this.unlock();
            return true;
        }
    }
}

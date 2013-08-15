#test shell author zhangyq
#date:2013/8/15
 files="hello worldi"
 echo "A is:"
 echo "$files"

#####################################
 factorial=1
 #for a in seq 1 10;
 #do
 #factorial=`expr $factorial \* $a`
 #done
 echo [10!= $factorial]
#####################################
#num=10
#while["$num"!="0"]
#do
#num=num-1
#num='expr"$num""-""1"'
#echo $num
#done 
for i in 1 2 3; do
echo $i
done
zyq=1;
for((i=1;i<=10;i++))
do
 zyq=$(($i*$zyq));
 echo "i:"+$i+"zyq:"+$zyq;
echo iiiiiiiiiii;
echo $i;
done

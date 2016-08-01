package com.example.repository;

import com.example.domain.AbstractOptimisticLockEntity;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by pyshankov on 01.08.16.
 */
@NoRepositoryBean
public abstract class OptimisticRepository<T extends AbstractOptimisticLockEntity, ID extends Serializable> implements BaseRepository<T,ID> {

    @Override
    public final T persist(T s) {
        try {
            prePersist(s);
            checkVersion(s);
         return performSave(s);
        } catch (Exception e) {
            e.printStackTrace();
          throw  new RuntimeException();
        }
    }

    protected abstract  T performSave(T s);

    protected abstract  T performGet(long property);


    private void checkVersion(T car) throws  Exception{
       T car2 = performGet(car.getId()) ;
        if (car2==null ) return;
        		if (  car.getLastVisibleVersion() != car2.getVersion() )  throw new RuntimeException("conflict");
		else {
                    Field[] fields = car2.getClass().getDeclaredFields();
                    for (int i = 0 ; i < fields.length; i++ ){
                        fields[i].setAccessible(true);
                        fields[i].set(car2,fields[i].get(car));
                    }
			System.out.println(car2);
		}
    }


    private void prePersist(T t) throws NoSuchFieldException, IllegalAccessException {
        if(t.getVersion()==null) return;
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field f : fields ){
            f.setAccessible(true);
            if(f.get(t) instanceof Iterable ){
                Iterable iterable = (Iterable) f.get(t);
                iterable.forEach(p -> {
                    if(p instanceof AbstractOptimisticLockEntity) {
                        AbstractOptimisticLockEntity ap= ((AbstractOptimisticLockEntity) p);
                        ap.setVersion(t.getVersion()+1);
                    }
                });
            }
        }
    }

}

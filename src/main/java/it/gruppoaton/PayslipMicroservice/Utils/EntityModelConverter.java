package it.gruppoaton.PayslipMicroservice.Utils;

import java.util.List;

public interface EntityModelConverter <M, E> {

    public M fromViewModel(E e);

    public List<M> fromViewModelList(List<E> eList);

    public E toViewModel(M m);

    public List<E> toViewModelList(List<M> mList);

}

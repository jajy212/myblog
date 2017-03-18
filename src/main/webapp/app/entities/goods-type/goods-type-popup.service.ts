import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { GoodsType } from './goods-type.model';
import { GoodsTypeService } from './goods-type.service';
@Injectable()
export class GoodsTypePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private goodsTypeService: GoodsTypeService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.goodsTypeService.find(id).subscribe(goodsType => {
                this.goodsTypeModalRef(component, goodsType);
            });
        } else {
            return this.goodsTypeModalRef(component, new GoodsType());
        }
    }

    goodsTypeModalRef(component: Component, goodsType: GoodsType): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.goodsType = goodsType;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}

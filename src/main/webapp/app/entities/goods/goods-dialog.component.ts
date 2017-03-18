import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Goods } from './goods.model';
import { GoodsPopupService } from './goods-popup.service';
import { GoodsService } from './goods.service';
import { GoodsType, GoodsTypeService } from '../goods-type';
@Component({
    selector: 'jhi-goods-dialog',
    templateUrl: './goods-dialog.component.html'
})
export class GoodsDialogComponent implements OnInit {

    goods: Goods;
    authorities: any[];
    isSaving: boolean;

    goodstypes: GoodsType[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private goodsService: GoodsService,
        private goodsTypeService: GoodsTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.goodsTypeService.query().subscribe(
            (res: Response) => { this.goodstypes = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.goods.id !== undefined) {
            this.goodsService.update(this.goods)
                .subscribe((res: Goods) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.goodsService.create(this.goods)
                .subscribe((res: Goods) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Goods) {
        this.eventManager.broadcast({ name: 'goodsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackGoodsTypeById(index: number, item: GoodsType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-goods-popup',
    template: ''
})
export class GoodsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private goodsPopupService: GoodsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.goodsPopupService
                    .open(GoodsDialogComponent, params['id']);
            } else {
                this.modalRef = this.goodsPopupService
                    .open(GoodsDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

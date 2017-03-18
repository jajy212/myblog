import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { GoodsType } from './goods-type.model';
import { GoodsTypePopupService } from './goods-type-popup.service';
import { GoodsTypeService } from './goods-type.service';
@Component({
    selector: 'jhi-goods-type-dialog',
    templateUrl: './goods-type-dialog.component.html'
})
export class GoodsTypeDialogComponent implements OnInit {

    goodsType: GoodsType;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private goodsTypeService: GoodsTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.goodsType.id !== undefined) {
            this.goodsTypeService.update(this.goodsType)
                .subscribe((res: GoodsType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.goodsTypeService.create(this.goodsType)
                .subscribe((res: GoodsType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: GoodsType) {
        this.eventManager.broadcast({ name: 'goodsTypeListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-goods-type-popup',
    template: ''
})
export class GoodsTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private goodsTypePopupService: GoodsTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.goodsTypePopupService
                    .open(GoodsTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.goodsTypePopupService
                    .open(GoodsTypeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

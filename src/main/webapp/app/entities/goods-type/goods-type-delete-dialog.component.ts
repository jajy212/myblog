import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { GoodsType } from './goods-type.model';
import { GoodsTypePopupService } from './goods-type-popup.service';
import { GoodsTypeService } from './goods-type.service';

@Component({
    selector: 'jhi-goods-type-delete-dialog',
    templateUrl: './goods-type-delete-dialog.component.html'
})
export class GoodsTypeDeleteDialogComponent {

    goodsType: GoodsType;

    constructor(
        private goodsTypeService: GoodsTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.goodsTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'goodsTypeListModification',
                content: 'Deleted an goodsType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-goods-type-delete-popup',
    template: ''
})
export class GoodsTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private goodsTypePopupService: GoodsTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.goodsTypePopupService
                .open(GoodsTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

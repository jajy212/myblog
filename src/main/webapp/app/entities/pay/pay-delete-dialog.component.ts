import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Pay } from './pay.model';
import { PayPopupService } from './pay-popup.service';
import { PayService } from './pay.service';

@Component({
    selector: 'jhi-pay-delete-dialog',
    templateUrl: './pay-delete-dialog.component.html'
})
export class PayDeleteDialogComponent {

    pay: Pay;

    constructor(
        private payService: PayService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.payService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'payListModification',
                content: 'Deleted an pay'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pay-delete-popup',
    template: ''
})
export class PayDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private payPopupService: PayPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.payPopupService
                .open(PayDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

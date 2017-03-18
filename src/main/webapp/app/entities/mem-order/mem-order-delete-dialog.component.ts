import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { MemOrder } from './mem-order.model';
import { MemOrderPopupService } from './mem-order-popup.service';
import { MemOrderService } from './mem-order.service';

@Component({
    selector: 'jhi-mem-order-delete-dialog',
    templateUrl: './mem-order-delete-dialog.component.html'
})
export class MemOrderDeleteDialogComponent {

    memOrder: MemOrder;

    constructor(
        private memOrderService: MemOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.memOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'memOrderListModification',
                content: 'Deleted an memOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mem-order-delete-popup',
    template: ''
})
export class MemOrderDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private memOrderPopupService: MemOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.memOrderPopupService
                .open(MemOrderDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { MemberCate } from './member-cate.model';
import { MemberCatePopupService } from './member-cate-popup.service';
import { MemberCateService } from './member-cate.service';

@Component({
    selector: 'jhi-member-cate-delete-dialog',
    templateUrl: './member-cate-delete-dialog.component.html'
})
export class MemberCateDeleteDialogComponent {

    memberCate: MemberCate;

    constructor(
        private memberCateService: MemberCateService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.memberCateService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'memberCateListModification',
                content: 'Deleted an memberCate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-member-cate-delete-popup',
    template: ''
})
export class MemberCateDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private memberCatePopupService: MemberCatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.memberCatePopupService
                .open(MemberCateDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MemOrder } from './mem-order.model';
import { MemOrderService } from './mem-order.service';

@Component({
    selector: 'jhi-mem-order-detail',
    templateUrl: './mem-order-detail.component.html'
})
export class MemOrderDetailComponent implements OnInit, OnDestroy {

    memOrder: MemOrder;
    private subscription: any;

    constructor(
        private memOrderService: MemOrderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.memOrderService.find(id).subscribe(memOrder => {
            this.memOrder = memOrder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}

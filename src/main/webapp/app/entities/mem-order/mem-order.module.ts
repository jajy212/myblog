import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JblogSharedModule } from '../../shared';

import {
    MemOrderService,
    MemOrderPopupService,
    MemOrderComponent,
    MemOrderDetailComponent,
    MemOrderDialogComponent,
    MemOrderPopupComponent,
    MemOrderDeletePopupComponent,
    MemOrderDeleteDialogComponent,
    memOrderRoute,
    memOrderPopupRoute,
    MemOrderResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...memOrderRoute,
    ...memOrderPopupRoute,
];

@NgModule({
    imports: [
        JblogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MemOrderComponent,
        MemOrderDetailComponent,
        MemOrderDialogComponent,
        MemOrderDeleteDialogComponent,
        MemOrderPopupComponent,
        MemOrderDeletePopupComponent,
    ],
    entryComponents: [
        MemOrderComponent,
        MemOrderDialogComponent,
        MemOrderPopupComponent,
        MemOrderDeleteDialogComponent,
        MemOrderDeletePopupComponent,
    ],
    providers: [
        MemOrderService,
        MemOrderPopupService,
        MemOrderResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JblogMemOrderModule {}

import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GoodsDetailComponent } from '../../../../../../main/webapp/app/entities/goods/goods-detail.component';
import { GoodsService } from '../../../../../../main/webapp/app/entities/goods/goods.service';
import { Goods } from '../../../../../../main/webapp/app/entities/goods/goods.model';

describe('Component Tests', () => {

    describe('Goods Management Detail Component', () => {
        let comp: GoodsDetailComponent;
        let fixture: ComponentFixture<GoodsDetailComponent>;
        let service: GoodsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [GoodsDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    GoodsService
                ]
            }).overrideComponent(GoodsDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GoodsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GoodsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Goods(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.goods).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

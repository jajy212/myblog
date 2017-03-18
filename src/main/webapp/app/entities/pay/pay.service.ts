import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Pay } from './pay.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class PayService {

    private resourceUrl = 'api/pays';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(pay: Pay): Observable<Pay> {
        let copy: Pay = Object.assign({}, pay);
        copy.payDate = this.dateUtils
            .convertLocalDateToServer(pay.payDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(pay: Pay): Observable<Pay> {
        let copy: Pay = Object.assign({}, pay);
        copy.payDate = this.dateUtils
            .convertLocalDateToServer(pay.payDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Pay> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.payDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.payDate);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].payDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].payDate);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}

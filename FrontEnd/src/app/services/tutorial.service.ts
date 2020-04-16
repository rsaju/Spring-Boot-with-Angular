import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TutorialService {

  baseUrl = 'http://localhost:8080/api/tutorials';

  constructor(private http: HttpClient) { }

  getAllTutorials(){
    return this.http.get(this.baseUrl);
  }

  getTutorialById(id) {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createTutorial(data) {
    return this.http.post(this.baseUrl,data);
  }

  updateTutorial(id, data) {
    return this.http.put(`${this.baseUrl}/${id}`, data);
  }

  deleteTutorial(id) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  deleteAlltutorial(){
    return this.http.delete(this.baseUrl);
  }

  findByTitle(title) {
    return this.http.get(`${this.baseUrl}?title=${title}`);
  }
}

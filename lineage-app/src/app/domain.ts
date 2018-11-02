export interface Company {
  id: number;
  name: string;
}

export interface Extract {
  id: number;
  decisionId: number;
  lang: string;
  sentences: string;
  relation: number;
}

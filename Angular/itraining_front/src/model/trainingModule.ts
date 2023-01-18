import { ILearnerAccount } from "./learnerAccount";
import { ISession } from "./session";

export interface ITrainingModule {
  id: number,
  title: string,
  startDate: Date,
  endDate: Date,
  sessionList: ISession[],
  registeredLearnerList: ILearnerAccount[]
}

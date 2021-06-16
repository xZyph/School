import mongoose from 'mongoose';
import { StringUtil } from '../utilities/string-util';
import bcrypt from 'bcrypt-nodejs';

const userSchema = new mongoose.Schema({
  username: String,
  first: String,
  last: String,
  password: String,
  email: String,
  userlevel: { type: Number, default: 3 }
});

userSchema.set('timestamps', true);

userSchema.virtual('fullName').get(function() {
  const first = StringUtil.capitalize(this.first.toLowerCase());
  const last = StringUtil.capitalize(this.last.toLowerCase());

  return `${first} ${last}`;
});

userSchema.statics.passwordMatches = function(password, hash) {
  return bcrypt.compareSync(password, hash);
}

userSchema.pre('save', function(next) {
  this.username = this.username.toLowerCase();
  this.first = this.first;
  this.last = this.last;
  this.email = this.email.toLowerCase();

  const unsafePass = this.password;
  this.password = bcrypt.hashSync(unsafePass);

  mongoose.model('user', userSchema).findOne({ username: this.username }, (error, user) => {
    if(user) {
      next(new Error("Username already exists."));
    }

    next();
  })
})

export default mongoose.model('user', userSchema);